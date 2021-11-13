package khuvid19.vaccinated.LoginUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import khuvid19.vaccinated.LoginUser.Data.GoogleUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    public GoogleUser getInfoByToken(String access_token) {

        String url = infoUrl+"?access_token="+access_token;

        GoogleUser response = restTemplate.getForObject(url, GoogleUser.class);

        return response;
    }
}
