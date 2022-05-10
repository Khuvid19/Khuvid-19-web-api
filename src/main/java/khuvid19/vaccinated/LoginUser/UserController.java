package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import khuvid19.vaccinated.LoginUser.Data.*;

import khuvid19.vaccinated.LoginUser.Data.DTO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.swing.text.html.Option;
import java.util.List;
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
    private final ModelMapper modelMapper;

    @PostMapping("/user")
    public User setUserInfo(@RequestBody User user) {
        return userService.setUserInfo(user);
    }

    @PostMapping("/google")
    public User loginByToken(@RequestBody Token access_token) {
        User user = userService.oauthLogin(access_token.getAccess_token());
        return user;
    }

    @GetMapping("/user")
    public boolean isNickNameExists(@RequestParam String nickName) {
        return userRepository.existsByNickName(nickName);
    }


    @GetMapping("/types/age")
    public List getAgeTypes() {
        return AgeType.getAllTypes();
    }

    @GetMapping("/types/gender")
    public List<Map> getGenderType() {
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

    @PostMapping("/child")
    public ResponseEntity uploadChildInfo(@RequestBody ChildInfo childInfo, @ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        User user = securityUser.getUser();
        Child child = modelMapper.map(childInfo, Child.class);
        return userService.addChild(user, child);

    }

}
