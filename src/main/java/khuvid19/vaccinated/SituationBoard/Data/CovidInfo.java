package khuvid19.vaccinated.SituationBoard.Data;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Entity
public class CovidInfo {

    @Id @GeneratedValue
    private Long id;

    private LocalDate date;
    private Integer decideCnt;

    public CovidInfo(LocalDate date, Integer decideCnt) {
        this.date = date;
        this.decideCnt = decideCnt;
    }

    public CovidInfo() {

    }
}
