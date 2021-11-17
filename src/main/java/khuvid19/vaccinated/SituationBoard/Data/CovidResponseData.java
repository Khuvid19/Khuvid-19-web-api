package khuvid19.vaccinated.SituationBoard.Data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@XmlRootElement(name = "item")
@NoArgsConstructor
@Getter
@Setter
public class CovidResponseData {


    private Float accDefRate;
    private Integer accExamCnt;
    private Integer accExamCompCnt;
    private Integer careCnt;
    private Integer clearCnt;
    private String createDt;
    private Integer deathCnt;
    private Integer decideCnt;
    private Integer examCnt;
    private Integer resultNegCnt;
    private Integer seq;
    private String stateDt;
    private String stateTime;
    private String updateDt;

}
