package khuvid19.vaccinated.SimpleReview;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
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
    SimpleReviewService simpleReviewService;

    @Autowired
    SideEffectsService sideEffectsService;

    @GetMapping
    public List<SimpleReview> getSimpleList() {
        return simpleReviewService.getAllSimpleReviews();
    }

    @PostMapping
    public void postNewSimpleReview(@RequestBody SimpleReview receivedReview) {
        simpleReviewService.insertSimpleReview(receivedReview);
    }

    @GetMapping(path = "/sideEffects")
    public Map<String, Integer> getAllSideEffectsCounts(@RequestParam(name = "vaccine") VaccineType vaccineType) {
        return sideEffectsService.getAllSideEffectsCountsByVaccine(vaccineType);
    }

}