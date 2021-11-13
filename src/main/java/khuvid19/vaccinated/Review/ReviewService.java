package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.ReviewRepository;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SideEffectsService sideEffectsService;

    public List<Review> getAllSimpleReviews() {
        return reviewRepository.findAll();
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
