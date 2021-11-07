package khuvid19.vaccinated.controller;

import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Value("${sns.google.url}")
    private String ENDPOINT;
    @Value("${sns.google.client.id}")
    private String CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String REDIRECT_URI;
    @Value("${sns.google.scope}")
    private String SCOPE;
    private static final String RESPONSE_TYPE = "code";


    @GetMapping("/auth/google")
    public User oauthLogin(String code) throws ChangeSetPersister.NotFoundException {
        log.info("code : {}", code);
        User user = userService.oauthLogin(code);

        return user;
    }

    @PostMapping("/auth/google")
    public HttpStatus setUserName(@RequestParam String email, @RequestParam String userName) {
        return userService.setUserName(email, userName);

    }
}
