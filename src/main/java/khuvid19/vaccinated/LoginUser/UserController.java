package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.Data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/auth/user")
    public HttpStatus setUserName(@RequestParam String userToken, @RequestParam String userName) {
        return userService.setUserName(userToken, userName);
    }

    @PostMapping("/auth/google")
    public User loginByToken(@RequestParam(value = "access_token") String access_token) {
        User user = userService.oauthLogin(access_token);
        return user;
    }

    @PostMapping("/login")
    public User log() {
        User user = new User("Juhee", "lskdjff", "salkdfjlaksdfja", "sldkjfslkdjf");
        userRepository.save(user);
        user.setJwtToken(jwtTokenProvider.createToken(user));
        return user;
    }

    @GetMapping("/login")
    public SecurityUser log2(@AuthenticationPrincipal SecurityUser user) {
        return user;
    }


    @DeleteMapping("/auth/google")
    public HttpStatus logout() {
        return HttpStatus.OK;
    }
}
