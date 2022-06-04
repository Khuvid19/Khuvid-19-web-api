package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.ReviewType;
import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.ChildRepository;
import khuvid19.vaccinated.LoginUser.Data.Child;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.Review.Data.*;
import khuvid19.vaccinated.Review.Data.DTO.*;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ChildRepository childRepository;
    private final SideEffectsService sideEffectsService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public Page<ReviewCard> getPagedReview(int pageIndex) {
        PageRequest request = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        return reviewRepository.findAll(request)
                .map(review -> modelMapper.map(review, ReviewCard.class));
    }

    public Page<ReviewCard> searchPagedReview(int pageIndex, ReviewFilter filters) {
        PageRequest paging = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        Specification<Review> specification = SearchReviewSpecs.initial();
        boolean isDetailVaccine = false;

        if (filters.getVaccines() != null) {
            specification = specification.and(SearchReviewSpecs.vaccineContains(filters.getVaccines()));
        }

        if (filters.getDetailDisc() != null) {
            String searchWord = filters.getDetailDisc();
            List<VaccineType> vaccineTypes = new ArrayList<>();
            for (VaccineType type : VaccineType.values()) {
                if (type.getKoreanName().contains(searchWord)) {
                    vaccineTypes.add(type);
                    isDetailVaccine = true;
                }
            }

            if (isDetailVaccine) {
                specification = specification.and(SearchReviewSpecs.vaccineContains(vaccineTypes));
            }

            specification = specification.or(SearchReviewSpecs.searchTextContains(filters.getDetailDisc()));
        }


        if (filters.getSideEffects() != null) {
            Specification<Review> effectsSpecification = SearchReviewSpecs.initial();
            for (SideEffectType type : filters.getSideEffects()) {
                effectsSpecification = effectsSpecification.or(SearchReviewSpecs.sideEffectContain(type));
            }
            specification = specification.and(effectsSpecification);
    }

        if (filters.getStartInoculated() != null || filters.getEndInoculated() != null) {
            specification = specification.and(SearchReviewSpecs.inoculatedBetween(filters.getStartInoculated(), filters.getEndInoculated()));
        }

        if (filters.getAuthorGenders() != null) {
            specification = specification.and(SearchReviewSpecs.genderContains(filters.getAuthorGenders()));
        }
        
        if (filters.getAuthorAges() != null) {
            specification = specification.and(SearchReviewSpecs.ageContains(filters.getAuthorAges()));
        }

        Page<Review> all = reviewRepository.findAll(specification, paging);
        return all
                .map(review -> modelMapper.map(review, ReviewCard.class));
    }

    public List<ReviewCard> getMyReviews(Long userId) {
        return reviewRepository.findAllByAuthor_Id(userId).stream()
                .map(review -> modelMapper.map(review, ReviewCard.class))
                .collect(Collectors.toList());
    }

    public ResponseEntity insertReview(Review receivedReview, User user) {
        List<SideEffectType> inputSideEffectTypes = receivedReview.getSideEffects();
        VaccineType inputVaccineType = receivedReview.getVaccine();


        Boolean isDuplicatedReview = reviewRepository.existsReviewsByAuthor_IdAndVaccine(user.getId(), receivedReview.getVaccine());
//        Boolean isDuplicated = reviewRepository.existsByReviewTargetIdAndVaccine(receivedReview.getReviewTargetId(), inputVaccineType);

        if (isDuplicatedReview) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        try {
            log.info("SETTED");
            log.info(receivedReview.getReviewTargetType().toString());
        } catch (NullPointerException e) {
            log.info("Default MySELF");
            receivedReview.setReviewTargetType(ReviewType.MYSELF);
            receivedReview.setReviewTargetId(user.getId());
        }

        ReviewType target = receivedReview.getReviewTargetType();
        if (target.equals(ReviewType.CHILD) && !inputVaccineType.getKoreanName().contains("소아")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (target.equals(ReviewType.MYSELF) && inputVaccineType.getKoreanName().contains("소아")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        receivedReview.setAuthor(user);
        reviewRepository.save(receivedReview);
        if (receivedReview.getSideEffects() != null) {
            sideEffectsService.addSideEffectsCount(inputSideEffectTypes, inputVaccineType);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity updateReview(ReviewInput inputReview, User user) {
        Long targetId = inputReview.getId();
        Optional<Review> optionalFoundReview = reviewRepository.findById(targetId);
        if (optionalFoundReview.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        Review foundReview = optionalFoundReview.get();

        if (!foundReview.getAuthor().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        sideEffectsService.updateSideEffectsCount(foundReview.getSideEffects(), foundReview.getVaccine(),
                inputReview.getSideEffects(), inputReview.getVaccine());

        modelMapper.map(inputReview, foundReview);
        if (inputReview.getSideEffects().isEmpty()) {
            foundReview.setSideEffects(new ArrayList<>());
        }
        reviewRepository.save(foundReview);

        ReviewCard modifiedReviewCard = modelMapper.map(foundReview, ReviewCard.class);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity removeReview(Long reviewId, Long userId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        if (!optionalReview.get().getAuthor().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        Review review = optionalReview.get();
        sideEffectsService.subtractSideEffectCount(review.getSideEffects(), review.getVaccine());
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<ReviewUser> getAllReviewers(User user) {
        List<ReviewUser> reviewUsers = new ArrayList<>();
        ReviewUser self = modelMapper.map(user, ReviewUser.class);
        self.setType(ReviewType.MYSELF);
        reviewUsers.add(self);

        Optional<Child> childByParent_id = childRepository.findChildByParent_Id(user.getId());
        List<ReviewUser> child = childByParent_id.stream()
                .map(c -> modelMapper.map(c, ReviewUser.class))
                .peek(u -> u.setType(ReviewType.CHILD))
                .collect(Collectors.toList());

        reviewUsers.addAll(child);

        return reviewUsers;
    }

    public List<SimilarReviewCard> getSimilarity(Long userId, Long reviewID, Integer page) {

        String query = "select a.*,\n" +
                "(select count(*)  from (select t1.side_effects\n" +
                "       from review_side_effects AS t1 JOIN review_side_effects AS t2 ON t1.side_effects = t2.side_effects \n" +
                "       where t1.review_id = a.id and t2.review_id =b.id) f)  / \n" +
                "(select count(*) \n" +
                " from \n" +
                " (select side_effects from review_side_effects s1\n" +
                "where s1.review_id = a.id union select s2.side_effects from review_side_effects s2 where s2.review_id = b.id) c) *100 as similarity \n" +
                "from review a, (select * from review where id = %d ) b where a.id != b.id and a.vaccine = b.vaccine\n" +
                "order by similarity desc";

        String result = String.format(query, reviewID);
        Query searchSimilarity = entityManager.createNativeQuery(
                result, "searchSimilarity");

        List<Object[]> resultList = searchSimilarity.setFirstResult(page * 10)
                .setMaxResults(10)
                .getResultList();

        List<SimilarReviewCard> similarityList = new ArrayList<>();

        for (Object[] similarResult : resultList) {
            Review review = (Review) similarResult[0];
            Long similarity = (Long) similarResult[1];

            similarityList.add(
                    new SimilarReviewCard(
                            modelMapper.map(review, ReviewCard.class),
                            similarity
                    )
            );
        }

        return similarityList;
    }
}
