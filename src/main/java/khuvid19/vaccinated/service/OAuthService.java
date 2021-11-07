package khuvid19.vaccinated.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import khuvid19.vaccinated.dto.login.GoogleUser;
import khuvid19.vaccinated.dto.login.OAuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OAuthService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${sns.google.callback.url}") private String REDIRECT_URI;
    @Value("${sns.google.client.id}") private String CLIENT_ID;
    @Value("${sns.google.client.secret}") private String CLIENT_SECRET;
    @Value("${sns.google.token.url}") String url;
    @Value("${sns.google.token.info.url}") String infoUrl;
    private static final String GRANT_TYPE = "authorization_code";


    public OAuthService(RestTemplate restTemplate) {
        this.objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.restTemplate = restTemplate;
    }

    public GoogleUser getInfoByToken(String access_token) {

        String url = infoUrl+"?access_token="+access_token;

        GoogleUser response = restTemplate.getForObject(url, GoogleUser.class);

        return response;
    }
}
