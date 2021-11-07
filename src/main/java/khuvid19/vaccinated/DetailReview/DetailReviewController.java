package khuvid19.vaccinated.DetailReview;

import khuvid19.vaccinated.DetailReview.Data.DetailReview;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import khuvid19.vaccinated.SimpleReview.Data.SimpleReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review/detail")
public class DetailReviewController {

    @Autowired
    SideEffectsService sideEffectsService;

    @Autowired
    DetailReviewRepository detailReviewRepository;

    @PostMapping
    public void postNewDetailReview(@RequestBody DetailReview inputDetailReview) {
        SimpleReview simpleReview = inputDetailReview.getTags();
        detailReviewRepository.save(inputDetailReview);
        sideEffectsService.addSideEffectsCount(simpleReview.getSideEffectTypes(), simpleReview.getVaccine());
    }
}
