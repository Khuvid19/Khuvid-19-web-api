package khuvid19.vaccinated.LoginUser;

import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.Data.GoogleUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final OAuthService oAuthService;

    public UserService(UserRepository userRepository, OAuthService oAuthService) {
        this.userRepository = userRepository;
        this.oAuthService = oAuthService;
    }

    public User oauthLogin(String access_token){

        GoogleUser googleUser = oAuthService.getInfoByToken(access_token);
        log.info("Google User Name : {}", googleUser.getName());

        Optional<User> user = userRepository.findByEmail(googleUser.getEmail());
        if (user.isEmpty()){
            User newUser = googleUser.toUser(access_token);
            userRepository.save(newUser);
            return newUser;
        }

        User existingUser = user.get();
        return userRepository.save(existingUser.setAccessToken(access_token));
    }


    public HttpStatus setUserName(String token, String nickName) {

        if (userRepository.existsByNickName(nickName)){
            return HttpStatus.FORBIDDEN;
        }
        Optional<User> user = userRepository.findByAccessToken(token);
        User setUser = user.get();

        setUser.setNickName(nickName);
        userRepository.save(setUser);

        return HttpStatus.OK;
    }

}
