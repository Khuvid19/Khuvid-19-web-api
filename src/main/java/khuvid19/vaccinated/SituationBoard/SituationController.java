package khuvid19.vaccinated.SituationBoard;

import khuvid19.vaccinated.SituationBoard.Data.CovidData;
import khuvid19.vaccinated.SituationBoard.Data.CovidResponse;
import khuvid19.vaccinated.SituationBoard.Data.CovidResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SituationController {

    @Value("${portal.secretkey}")
    private String portalKey;
    @Value("${portal.url}")
    private String portalUrl;

    private final CovidRepository covidRepository;


    @GetMapping("/test")
    public Object get() throws UnsupportedEncodingException {

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String serviceKey_Decoder = URLDecoder.decode(portalKey.toString(), "UTF-8");

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
        return covidRepository.save(todayData);
    }

    @GetMapping("/yesterday")
    public Object yester() throws UnsupportedEncodingException {

        LocalDate today = LocalDate.now().minusDays(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String serviceKey_Decoder = URLDecoder.decode(portalKey.toString(), "UTF-8");

        String url = portalUrl + "?ServiceKey=" + serviceKey_Decoder + "&startCreateDt=" + today.format(formatter) + "&endCreateDt=" + today.format(formatter);

        log.info(url);

        RestTemplate restTemplate = new RestTemplate();
        CovidResponse response = restTemplate.getForObject(url, CovidResponse.class);

        CovidResponseData covidResponseData = response.getBody().getItems().get(0);

        CovidData todayData = new CovidData(LocalDate.parse(covidResponseData.getStateDt(), formatter), covidResponseData.getDecideCnt());

        return covidRepository.save(todayData);

    }

    @GetMapping("/covid")
    public List<CovidData> getCovidData(){
        LocalDate today = LocalDate.now();

        Optional<CovidData> todayDate = covidRepository.findByDate(today);
        List<CovidData> covidList = new ArrayList<>();

        if (todayDate.isPresent()) {
            covidList.add(todayDate.get());
            Optional<CovidData> yesterday = covidRepository.findByDate(today.minusDays(1));

            covidList.add(yesterday.get());
        }else{
            Optional<CovidData> yesterday = covidRepository.findByDate(today.minusDays(1));
            Optional<CovidData> yesterday2 = covidRepository.findByDate(today.minusDays(2));

            covidList.add(yesterday.get());
            covidList.add(yesterday2.get());
        }
        return covidList;
    }
}
