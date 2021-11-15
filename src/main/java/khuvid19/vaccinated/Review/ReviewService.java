package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.ReviewFilter;
import khuvid19.vaccinated.Review.Data.ReviewRepository;
import khuvid19.vaccinated.Review.Data.SearchReviewSpecs;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SideEffectsService sideEffectsService;

    public Page<Review> getPagedReview(int pageIndex) {
        PageRequest request = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        return reviewRepository.findAll(request);
    }

    public Page<Review> searchPagedReview(int pageIndex, ReviewFilter filters) {
        PageRequest paging = PageRequest.of(pageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        Specification<Review> specification = SearchReviewSpecs.initial();

        if (filters.getVaccine() != null) {
            specification.and(SearchReviewSpecs.vaccineEqual(filters.getVaccine()));
        }

        if (filters.getSideEffects() != null) {
            specification.and(SearchReviewSpecs.sideEffectContains(filters.getSideEffects()));
        }

        if (filters.getStartInoculated() != null || filters.getEndInoculated() != null) {
            specification.and(SearchReviewSpecs.inoculatedBetween(filters.getStartInoculated(), filters.getEndInoculated()));
        }

        return reviewRepository.findAll(specification, paging);
    }

    public List<Review> getMyReviews(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    public HttpStatus insertSimpleReview(Review receivedReview) {
        List<SideEffectType> inputSideEffectTypes = receivedReview.getSideEffects();
        VaccineType inputVaccineType = receivedReview.getVaccine();

        Boolean isDuplicatedReview = reviewRepository.existsReviewByUserIdAndVaccine(
                receivedReview.getUserId(), receivedReview.getVaccine()
        );

        if (isDuplicatedReview) {
            return HttpStatus.GONE;
        }

        reviewRepository.save(receivedReview);
        sideEffectsService.addSideEffectsCount(inputSideEffectTypes, inputVaccineType);
        return HttpStatus.OK;
    }
}
