package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.Data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

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
        user.setJwtToken(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getNickName()));

        return user;
    }

    @GetMapping("/login")
    public String log2() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.toString();
    }


    @DeleteMapping("/auth/google")
    public HttpStatus logout() {
        return HttpStatus.OK;
    }
}
