package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.Review.Data.DTO.ReviewInput;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.Review.Data.DTO.ReviewCard;
import khuvid19.vaccinated.Review.Data.DTO.ReviewFilter;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final SideEffectsService sideEffectsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<ReviewCard> getSimpleList(@RequestParam Integer page) {
        return reviewService.getPagedReview(page);
    }

    @PostMapping
    public HttpStatus postNewReview(@RequestBody ReviewInput inputReview,
                                    @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        Review mappedReview = modelMapper.map(inputReview, Review.class);
        return reviewService.insertReview(mappedReview, securityUser.getUser());
    }

    @PutMapping
    public HttpStatus putReview(@RequestBody ReviewInput updateReview,
                                @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        return reviewService.updateReview(updateReview, securityUser.getUser());
    }

    @PostMapping("/search")
    public Page<ReviewCard> searchReviews(@RequestParam Integer page, @RequestBody ReviewFilter filters) {
        return reviewService.searchPagedReview(page, filters);
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

    @GetMapping(path = "/my")
    public List<ReviewCard> getMyReviews(@ApiIgnore @AuthenticationPrincipal SecurityUser user) {
        Long requestUserId = user.getUser().getId();
        return reviewService.getMyReviews(requestUserId);
    }
}