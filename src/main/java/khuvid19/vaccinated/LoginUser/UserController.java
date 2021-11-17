package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import khuvid19.vaccinated.LoginUser.Data.Token;
import khuvid19.vaccinated.LoginUser.Data.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    public User setUserInfo(@RequestBody User user) {
        return userService.setUserInfo(user);
    }

    @PostMapping("/google")
    public User loginByToken(@RequestBody Token token) {
        User user = userService.oauthLogin(token.getAccess_token());
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
}
