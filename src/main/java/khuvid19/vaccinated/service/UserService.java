package khuvid19.vaccinated.service;

import khuvid19.vaccinated.JwtTokenProvider;
import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.dto.login.GoogleUser;
import khuvid19.vaccinated.dto.login.OAuthToken;
import khuvid19.vaccinated.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final JwtTokenProvider jwtTokenProvider;


    public User oauthLogin(String oAuthToken) throws ChangeSetPersister.NotFoundException {

        GoogleUser googleUser = oAuthService.getUserInfo(oAuthToken);
        log.info("Google User Name : {}", googleUser.getName());

        if (!isJoinedUser(googleUser)) {
            User user = googleUser.toUser(oAuthToken);
            userRepository.save(user);
        }
        User user = userRepository.findByEmail(googleUser.getEmail()).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return user;
    }

    private boolean isJoinedUser(GoogleUser googleUser) {
        Optional<User> users = userRepository.findByEmail(googleUser.getEmail());
        return users.isPresent();
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