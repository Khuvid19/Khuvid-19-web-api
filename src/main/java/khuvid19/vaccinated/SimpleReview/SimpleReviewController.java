package khuvid19.vaccinated.SimpleReview;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import khuvid19.vaccinated.SimpleReview.Data.SimpleReview;
import khuvid19.vaccinated.SimpleReview.Data.SimpleReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review/simple")
public class SimpleReviewController {

    @Autowired
    SimpleReviewRepository simpleReviewRepository;

    @Autowired
    SideEffectsService sideEffectsService;

    @GetMapping
    public List<SimpleReview> getSimpleList() {
        return simpleReviewRepository.findAll();
    }

    @PostMapping
    public void postNewSimpleReview(@RequestBody SimpleReview receivedReview) {
        List<SideEffectType> inputSideEffectTypes = receivedReview.getSideEffectTypes();
        simpleReviewRepository.save(receivedReview);
        sideEffectsService.addSideEffectsCount(inputSideEffectTypes);
    }

    @GetMapping(path = "/sideEffects")
    public Map<String, Integer> getAllSideEffectsCounts() {
        return sideEffectsService.getAllSideEffectsCounts();
    }

}