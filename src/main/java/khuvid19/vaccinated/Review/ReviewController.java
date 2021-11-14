package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.ReviewFilter;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService ReviewService;
    private final SideEffectsService sideEffectsService;

    @GetMapping
    public Page<Review> getSimpleList(@RequestParam Integer page) {
        return ReviewService.getPagedReview(page);
    }

    @PostMapping
    public HttpStatus postNewSimpleReview(@RequestBody Review receivedReview, @AuthenticationPrincipal SecurityUser securityUser) {
//        Long userId = securityUser.getUser().getId();
//        receivedReview.setUserId(userId);
        return ReviewService.insertSimpleReview(receivedReview);
    }

    @PostMapping("/search")
    public Page<Review> searchReviews(@RequestParam Integer page, @RequestBody ReviewFilter filters) {
        return ReviewService.searchPagedReview(page, filters);
    }


    @GetMapping(path = "/sideEffects")
    public Map<String, Integer> getAllSideEffectsCounts(@RequestParam(name = "vaccine") VaccineType vaccineType) {
        return sideEffectsService.getAllSideEffectsCountsByVaccine(vaccineType);
    }

    @GetMapping(path = "/types/sideEffects")
    public Map<SideEffectType, String> getAllSideEffectTypes() {
        return SideEffectType.getAllTypes();
    }

    @GetMapping(path = "/types/vaccine")
    public Map<VaccineType, String> getAllVaccineTypes() {
        return VaccineType.getAllTypes();
    }
}