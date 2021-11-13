package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.ReviewRepository;
import lombok.RequiredArgsConstructor;
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

    public void insertSimpleReview(Review receivedReview) {
        List<SideEffectType> inputSideEffectTypes = receivedReview.getSideEffects();
        VaccineType inputVaccineType = receivedReview.getVaccine();
        reviewRepository.save(receivedReview);
        sideEffectsService.addSideEffectsCount(inputSideEffectTypes, inputVaccineType);
    }
}
