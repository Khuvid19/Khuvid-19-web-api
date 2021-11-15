package khuvid19.vaccinated.SituationBoard;

import com.fasterxml.jackson.databind.util.JSONPObject;
import khuvid19.vaccinated.SituationBoard.Data.CovidData;
import khuvid19.vaccinated.SituationBoard.Data.CovidInfo;
import khuvid19.vaccinated.SituationBoard.Data.CovidResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@RequiredArgsConstructor
@Slf4j
public class GetSituationData {

    private final CovidInfoRepository covidInfoRepository;

    @Value("${portal.secretkey}") private String portalKey;
    @Value("${portal.url}") private String portalUrl;

    @Scheduled(cron = "0 0 10 * * *")
    public void getData(){
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("ServiceKey", portalKey);
        params.add("startCreateDt",df.format(today));

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(portalUrl, String.class, params);

    }
}
