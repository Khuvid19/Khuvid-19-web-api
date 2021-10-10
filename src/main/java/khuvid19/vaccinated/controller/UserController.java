package khuvid19.vaccinated.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Value("${sns.google.url}")
    private String ENDPOINT;
    @Value("${sns.google.client.id}")
    private String CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String REDIRECT_URI;
    @Value("${sns.google.scope}")
    private String SCOPE;
    private static final String RESPONSE_TYPE = "code";

    @GetMapping("/users/login")
    public String login() {
        log.info("login request");
        return ENDPOINT + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE + "&scope=" + SCOPE;
    }

    @GetMapping("/auth/google")
    public String logged(){
        log.info("logged");
        return "logged";
    }
}
