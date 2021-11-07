package khuvid19.vaccinated.service;

import khuvid19.vaccinated.JwtTokenProvider;
import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.dto.login.GoogleUser;
import khuvid19.vaccinated.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, OAuthService oAuthService, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.oAuthService = oAuthService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User oauthLogin(String access_token){

        GoogleUser googleUser = oAuthService.getInfoByToken(access_token);
        log.info("Google User Name : {}", googleUser.getName());

        Optional<User> user = userRepository.findByAccessToken(access_token);
        if (user.isEmpty()){
            User newUser = googleUser.toUser(access_token);
            userRepository.save(newUser);
            return newUser;
        }

        User existingUser = user.get();
        return userRepository.save(existingUser.setAccessToken(access_token));
    }


    public HttpStatus setUserName(String token, String userName) {

        if (userRepository.existsByUserName(userName)){
            return HttpStatus.FORBIDDEN;
        }
        Optional<User> user = userRepository.findByAccessToken(token);
        User setUser = user.get();

        setUser.setUserName(userName);
        userRepository.save(setUser);

        return HttpStatus.OK;
    }

}
