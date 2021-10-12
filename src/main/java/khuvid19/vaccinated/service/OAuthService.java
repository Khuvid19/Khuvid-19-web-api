package khuvid19.vaccinated.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import khuvid19.vaccinated.dto.GoogleUser;
import khuvid19.vaccinated.dto.OAuthToken;
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

    public ResponseEntity<String> createPostRequest(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("grant_type", GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

    public OAuthToken getAccessToken(ResponseEntity<String> response) {
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oAuthToken;
    }

    public GoogleUser getUserInfo(OAuthToken oAuthToken) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", oAuthToken.getAccessToken());

        GoogleUser googleUser =  restTemplate.getForObject(infoUrl+"?access_token="+oAuthToken.getAccessToken(), GoogleUser.class);

        return googleUser;
    }
}
