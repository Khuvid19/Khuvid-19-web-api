package khuvid19.vaccinated.SimpleReview;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import khuvid19.vaccinated.SimpleReview.Data.SimpleReview;
import khuvid19.vaccinated.SimpleReview.Data.SimpleReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleReviewService {
    @Autowired
    SimpleReviewRepository simpleReviewRepository;

    @Autowired
    SideEffectsService sideEffectsService;

    public List<SimpleReview> getAllSimpleReviews() {
        return simpleReviewRepository.findAll();
    }

    public void insertSimpleReview(SimpleReview receivedReview) {
        List<SideEffectType> inputSideEffectTypes = receivedReview.getSideEffectTypes();
        VaccineType inputVaccineType = receivedReview.getVaccine();
        simpleReviewRepository.save(receivedReview);
        sideEffectsService.addSideEffectsCount(inputSideEffectTypes, inputVaccineType);
    }
}
