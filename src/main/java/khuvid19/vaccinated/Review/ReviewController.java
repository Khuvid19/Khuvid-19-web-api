package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService ReviewService;
    private final SideEffectsService sideEffectsService;

    @GetMapping
    public List<Review> getSimpleList() {
        return ReviewService.getAllSimpleReviews();
    }

    @PostMapping
    public HttpStatus postNewSimpleReview(@RequestBody Review receivedReview, @AuthenticationPrincipal SecurityUser securityUser) {
        Long userId = securityUser.getUser().getId();
        receivedReview.setUserId(userId);
        return ReviewService.insertSimpleReview(receivedReview);
    }

    @GetMapping(path = "/sideEffects")
    public Map<String, Integer> getAllSideEffectsCounts(@RequestParam(name = "vaccine") VaccineType vaccineType) {
        return sideEffectsService.getAllSideEffectsCountsByVaccine(vaccineType);
    }

}