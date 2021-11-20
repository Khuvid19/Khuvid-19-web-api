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
        if (user.isPresent()){
            logUser = user.get();
            logUser.setAccessToken(access_token);
            logUser.setJwtToken(jwtTokenProvider.createToken(logUser));
            return userRepository.save(logUser);
        } else {
            log.info("New User : {}", googleUser.getEmail());
            return null;
        }

    }

    public User setUserInfo(User user) {

        User logUser = user;
        logUser.setJwtToken(jwtTokenProvider.createToken(logUser));
        return userRepository.save(logUser);

    }

    public User DummyService(String dummyString){
        User dummyUser = new User(dummyString, dummyString, "access_token", "picURl");
        dummyUser = userRepository.save(dummyUser);
        dummyUser.setJwtToken(jwtTokenProvider.createToken(dummyUser));
        return dummyUser;
    }


}
