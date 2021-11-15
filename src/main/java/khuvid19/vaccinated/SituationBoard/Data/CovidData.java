package khuvid19.vaccinated.SituationBoard.Data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@XmlRootElement(name = "item")
@NoArgsConstructor
@Getter
public class CovidData {


    private Float accDefRate;
    private Integer accExamCnt;
    private Integer accExamCompCnt;
    private Integer careCnt;
    private Integer clearCnt;
    private LocalDateTime createDt;
    private Integer deathCnt;
    private Integer decideCnt;
    private Integer examCnt;
    private Integer resultNegCnt;
    private Integer seq;
    private Date stateDt;
    private Time stateTime;
    private LocalDateTime updateDt;

}
