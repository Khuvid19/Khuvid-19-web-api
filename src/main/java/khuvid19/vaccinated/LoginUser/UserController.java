package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.LoginUser.Data.PostUser;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.Data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/auth/user")
    public UserInfo setUserInfo(@AuthenticationPrincipal SecurityUser user, PostUser postUser) {
        return userService.setUserInfo(user.getUser(), postUser);
    }

    @GetMapping("/auth/user")
    public boolean isNickNameExists(@RequestParam String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @PostMapping("/auth/google")
    public User loginByToken(@RequestParam(value = "access_token") String access_token) {
        User user = userService.oauthLogin(access_token);
        return user;
    }


}
