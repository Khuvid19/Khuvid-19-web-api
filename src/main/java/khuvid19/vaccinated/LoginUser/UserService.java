package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.Configuration.JwtTokenProvider;
import khuvid19.vaccinated.LoginUser.Data.PostUser;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.Data.GoogleUser;
import khuvid19.vaccinated.LoginUser.Data.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final JwtTokenProvider jwtTokenProvider;


    public User oauthLogin(String access_token){

        GoogleUser googleUser = oAuthService.getInfoByToken(access_token);
        log.info("Google User Name : {}", googleUser.getName());

        User logUser;
        Optional<User> user = userRepository.findByEmail(googleUser.getEmail());
        if (user.isEmpty()){
            logUser = googleUser.toUser(access_token);
            userRepository.save(logUser );
        } else {
            logUser = user.get();
        }
        logUser.setAccessToken(access_token);
        logUser.setJwtToken(jwtTokenProvider.createToken(logUser));
        return userRepository.save(logUser);
    }

    public UserInfo setUserInfo(User user, PostUser postUser) {

        user.setNickName(postUser.getNickName());
        user.setGender(postUser.getGender());
        user.setAge(postUser.getAge());

        userRepository.save(user);

        return user.toUserInfo();

    }

    public User isUserExists(String access_token) {
        GoogleUser googleUser = oAuthService.getInfoByToken(access_token);
        Optional<User> user = userRepository.findByEmail(googleUser.getEmail());
        if (user.isEmpty()) {
            return null;
        }else{
            User logUser = user.get();
            logUser.setAccessToken(access_token);
            logUser.setJwtToken(jwtTokenProvider.createToken(logUser));
            return userRepository.save(logUser);
        }
    }

}
