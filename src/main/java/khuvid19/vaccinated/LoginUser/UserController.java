package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import khuvid19.vaccinated.LoginUser.Data.PostUser;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.LoginUser.Data.User;

import khuvid19.vaccinated.LoginUser.Data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("auth")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/user")
    public User setUserInfo(User user) {
        return userService.setUserInfo(user);
    }

    @PostMapping("/google")
    public User loginByToken(@RequestParam(value = "access_token") String access_token) {
        User user = userService.oauthLogin(access_token);
        return user;
    }

    @GetMapping("/user")
    public boolean isNickNameExists(@RequestParam String nickName) {
        return userRepository.existsByNickName(nickName);
    }


    @GetMapping("/types/age")
    public Map<AgeType, String> getAgeTypes() {
        return AgeType.getAllTypes();
    }

    @GetMapping("/types/gender")
    public Map<Gender, String> getGenderType() {
        return Gender.getAllTypes();
    }

    @GetMapping("/dummy")
    public User dummyLogin(@RequestParam String dummy) {

        return userService.DummyService(dummy);
    }

    @GetMapping
    public UserInfo getUserInfo(@ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        User user = securityUser.getUser();
        return user.toUserInfo();
    }

    @PutMapping
    public User reviseUserInfo(@RequestBody PostUser postUser, @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        Optional<User> user = userRepository.findById(securityUser.getUser().getId());
        if (user.isEmpty()) {
            return null;
        }

        User reviseUser = user.get();
        reviseUser.setAge(postUser.getAge());
        reviseUser.setNickName(postUser.getNickName());
        reviseUser.setGender(postUser.getGender());
        reviseUser.setJwtToken(tokenProvider.createToken(reviseUser));

        return userRepository.save(reviseUser);
    }
}
