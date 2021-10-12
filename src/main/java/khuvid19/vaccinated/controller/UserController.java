package khuvid19.vaccinated.controller;

import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.dto.UserInfo;
import khuvid19.vaccinated.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @GetMapping("/users/google")
    public String login() {
        log.info("login request");
        return ENDPOINT + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE + "&scope=" + SCOPE;
    }

    @GetMapping("/auth/google")
    public UserInfo oauthLogin(String code) throws ChangeSetPersister.NotFoundException {
        log.info("code : {}", code);
        UserInfo user = userService.oauthLogin(code);

        return user;
    }

    @PostMapping("/auth/google")
    public UserInfo setUserName(@RequestParam String email, @RequestParam String userName) {
        UserInfo user = userService.setUserName(email, userName);
        return user;
    }
}
