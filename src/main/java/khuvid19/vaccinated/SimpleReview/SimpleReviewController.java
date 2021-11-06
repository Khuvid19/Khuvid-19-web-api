package khuvid19.vaccinated.SimpleReview;

import khuvid19.vaccinated.SimpleReview.Data.SimpleReview;
import khuvid19.vaccinated.SimpleReview.Data.SimpleReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/simple")
public class SimpleReviewController {

    @Autowired
    SimpleReviewRepository simpleReviewRepository;

    @GetMapping
    public List<SimpleReview> getSimpleList() {
        return simpleReviewRepository.findAll();
    }

    @PostMapping
    public void postNewSimpleReview(@RequestBody SimpleReview receivedReview) {
        simpleReviewRepository.save(receivedReview);
    }

}