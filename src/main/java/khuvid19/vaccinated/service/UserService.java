package khuvid19.vaccinated.service;

import khuvid19.vaccinated.JwtTokenProvider;
import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.dto.GoogleUser;
import khuvid19.vaccinated.dto.OAuthToken;
import khuvid19.vaccinated.dto.UserInfo;
import khuvid19.vaccinated.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
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

    public UserInfo oauthLogin(String code) throws ChangeSetPersister.NotFoundException {
        ResponseEntity<String> accessTokenResponse = oAuthService.createPostRequest(code);
        OAuthToken oAuthToken = oAuthService.getAccessToken(accessTokenResponse);
        log.info("Access Token: {}", oAuthToken.getAccessToken());

        GoogleUser googleUser = oAuthService.getUserInfo(oAuthToken);
        log.info("Google User Name : {}", googleUser.getName());

        if (!isJoinedUser(googleUser)) {
            User user = googleUser.toUser(oAuthToken.getAccessToken());
            userRepository.save(user);
        }
        User user = userRepository.findByEmail(googleUser.getEmail()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return user.toUserInfo();
    }

    private boolean isJoinedUser(GoogleUser googleUser) {
        Optional<User> users = userRepository.findByEmail(googleUser.getEmail());
        return users.isPresent();
    }

    public UserInfo setUserName(String email, String userName) {
        Optional<User> user = userRepository.findByEmail(email);
        if( !user.isEmpty()){
            User updateUser = user.get().setUserName(userName);
            userRepository.save(updateUser);
        }
        return userRepository.findByEmail(email).get().toUserInfo();
    }

}
