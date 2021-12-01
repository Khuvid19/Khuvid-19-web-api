package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.Review.Data.DTO.ReviewCard;
import khuvid19.vaccinated.Review.Data.DTO.ReviewFilter;
import khuvid19.vaccinated.Review.Data.DTO.ReviewInput;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.ReviewRepository;
import khuvid19.vaccinated.Review.Data.SearchReviewSpecs;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SideEffectsService sideEffectsService;
    private final ModelMapper modelMapper;

    public Page<ReviewCard> getPagedReview(int pageIndex) {
        PageRequest request = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        return reviewRepository.findAll(request)
                .map(review -> modelMapper.map(review, ReviewCard.class));
    }

    public Page<ReviewCard> searchPagedReview(int pageIndex, ReviewFilter filters) {
        PageRequest paging = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        Specification<Review> specification = SearchReviewSpecs.initial();

        if (filters.getVaccines() != null) {
            specification = specification.and(SearchReviewSpecs.vaccineContains(filters.getVaccines()));
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

        if (filters.getDetailDisc() != null) {
            specification = specification.and(SearchReviewSpecs.searchTextContains(filters.getDetailDisc()));
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

        if (isDuplicatedReview) {
            return ResponseEntity.status(HttpStatus.GONE).build();
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
        reviewRepository.deleteById(reviewId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public void removeAllReviews() {
        reviewRepository.deleteAll();
        sideEffectsService.removeAllData();
    }

}
