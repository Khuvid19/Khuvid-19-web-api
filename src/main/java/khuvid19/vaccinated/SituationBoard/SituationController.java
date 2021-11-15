package khuvid19.vaccinated.SituationBoard;

import khuvid19.vaccinated.SituationBoard.Data.CovidData;
import khuvid19.vaccinated.SituationBoard.Data.CovidInfo;
import khuvid19.vaccinated.SituationBoard.Data.CovidResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SituationController {

    @Value("${portal.secretkey}") private String portalKey;
    @Value("${portal.url}") private String portalUrl;

    private final CovidInfoRepository infoRepo;

    @GetMapping("/covid")
    public CovidInfo getCovidInfo() {
        return infoRepo.findByDate(LocalDate.now()).get();
    }

    @GetMapping("/test")
    public CovidInfo get(){

        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("ServiceKey", portalKey);
        params.add("startCreateDt",df.format(today));

        String url = portalUrl + "?ServiceKey=" + portalKey + "&startCreateDt=" + df.format(today);

        RestTemplate restTemplate = new RestTemplate();
        CovidResponse response = restTemplate.getForObject(url, CovidResponse.class);
        log.info(response.getItems().getItems().toString());

        CovidData covidData = response.getItems().getItems().get(0);

        CovidInfo info = new CovidInfo(covidData.getCreateDt().toLocalDate(), covidData.getDecideCnt());

        return infoRepo.save(info);
    }
}
