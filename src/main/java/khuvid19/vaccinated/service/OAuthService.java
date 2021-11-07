package khuvid19.vaccinated.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import khuvid19.vaccinated.dto.login.GoogleUser;
import khuvid19.vaccinated.dto.login.OAuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OAuthService {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${sns.google.token.url}") String url;
    @Value("${sns.google.token.info.url}") String infoUrl;
    private static final String GRANT_TYPE = "authorization_code";


    public OAuthService(RestTemplate restTemplate) {
        this.objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.restTemplate = restTemplate;
    }

    public GoogleUser getUserInfo(String oAuthToken) {
        GoogleUser googleUser =  restTemplate.getForObject(infoUrl+"?access_token="+oAuthToken, GoogleUser.class);

        return googleUser;
    }
}
