package khuvid19.vaccinated.Review;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.LoginUser.Data.DTO.SecurityUser;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.Review.Data.DTO.*;
import khuvid19.vaccinated.Review.Data.Review;
import khuvid19.vaccinated.SideEffects.Data.DTO.SideEffectStatistic;
import khuvid19.vaccinated.SideEffects.SideEffectsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final SideEffectsService sideEffectsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public Page<ReviewCard> getSimpleList(@RequestParam Integer page) {
        return reviewService.getPagedReview(page);
    }

    @PostMapping
    public ResponseEntity postNewReview(@RequestBody ReviewInput inputReview,
                                        @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        Review mappedReview = modelMapper.map(inputReview, Review.class);
        return reviewService.insertReview(mappedReview, securityUser.getUser());
    }

    @PutMapping
    public ResponseEntity putReview(@RequestBody ReviewInput updateReview,
                                    @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        return reviewService.updateReview(updateReview, securityUser.getUser());
    }

    @DeleteMapping
    public ResponseEntity deleteReview(@RequestParam Long id,
                                       @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        return reviewService.removeReview(id, securityUser.getUser().getId());
    }

    @PostMapping("/search")
    public Page<ReviewCard> searchReviews(@RequestParam Integer page, @RequestBody ReviewFilter filters) {
        return reviewService.searchPagedReview(page, filters);
    }

    @GetMapping(path = "/sideEffects")
    public SideEffectStatistic getAllSideEffectsCounts(@RequestParam(name = "vaccine") VaccineType vaccineType) {
        return sideEffectsService.getAllSideEffectsCountsByVaccine(vaccineType);
    }

    @GetMapping(path = "/types/sideEffects")
    public List<Map<String, String>> getAllSideEffectTypes() {
        return SideEffectType.getAllTypes();
    }

    @GetMapping(path = "/types/vaccine")
    public List<Map<String, String>> getAllVaccineTypes() {
        return VaccineType.getAllTypes();
    }

    @GetMapping("/my/reviewer")
    public List<ReviewUser> getAvailableUser(@ApiIgnore @AuthenticationPrincipal SecurityUser user) {
        User requestUser = user.getUser();
        return reviewService.getAllReviewers(requestUser);
    }

    @GetMapping(path = "/my")
    public List<ReviewCard> getMyReviews(@ApiIgnore @AuthenticationPrincipal SecurityUser user) {
        Long requestUserId = user.getUser().getId();
        return reviewService.getMyReviews(requestUserId);
    }


    @GetMapping("/similarity")
    public List<SimilarReviewCard> getSimilarity(@ApiIgnore @AuthenticationPrincipal SecurityUser securityUser, @RequestParam Long reviewId, @RequestParam Integer page) {
        Long userId = securityUser.getUser().getId();
        return reviewService.getSimilarity(userId, reviewId, page);
    }
}