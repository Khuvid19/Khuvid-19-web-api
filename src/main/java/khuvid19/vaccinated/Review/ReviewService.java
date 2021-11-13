package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    SideEffectsService sideEffectsService;

    public List<Review> getAllSimpleReviews() {
        return reviewRepository.findAll();
    }

    public void insertSimpleReview(Review receivedReview) {
        List<SideEffectType> inputSideEffectTypes = receivedReview.getSideEffectTypes();
        VaccineType inputVaccineType = receivedReview.getVaccine();
        reviewRepository.save(receivedReview);
        sideEffectsService.addSideEffectsCount(inputSideEffectTypes, inputVaccineType);
    }
}
