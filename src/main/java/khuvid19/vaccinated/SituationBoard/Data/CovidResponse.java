package khuvid19.vaccinated.SituationBoard.Data;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name="response")
@Getter
@Setter
public class CovidResponse {
    private Map<String, String> header;
    private CovidItems items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
}
