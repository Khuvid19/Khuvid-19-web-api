package khuvid19.vaccinated.SituationBoard;

import khuvid19.vaccinated.SituationBoard.Data.CovidData;
import khuvid19.vaccinated.SituationBoard.Data.CovidResponse;
import khuvid19.vaccinated.SituationBoard.Data.CovidResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class GetSituationData {

    private final CovidRepository covidRepository;

    @Value("${portal.secretkey}") private String portalKey;
    @Value("${portal.url}") private String portalUrl;

    @Scheduled(cron = "0 0 10 * * *")
    public void getData() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String serviceKey_Decoder = URLDecoder.decode(portalKey, StandardCharsets.UTF_8);

        String url = portalUrl + "?ServiceKey=" + serviceKey_Decoder + "&startCreateDt=" + today.format(formatter);

        log.info(url);

        RestTemplate restTemplate = new RestTemplate();
        CovidResponse response = restTemplate.getForObject(url, CovidResponse.class);

        CovidResponseData covidResponseData = response.getBody().getItems().get(0);
        Optional<CovidData> yesterday = covidRepository.findByDate(LocalDate.now().minusDays(1));

        CovidData todayData;
        if (yesterday.isPresent()) {
            todayData = new CovidData(LocalDate.parse(covidResponseData.getStateDt(), formatter),
                    covidResponseData.getDecideCnt(), covidResponseData.getDecideCnt() - yesterday.get().getDecideCnt());
        } else {
            todayData = new CovidData(LocalDate.parse(covidResponseData.getStateDt(), formatter), covidResponseData.getDecideCnt());

        }
        covidRepository.save(todayData);
    }
}
