package khuvid19.vaccinated.SituationBoard.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class CovidData {
    @Id @Column @GeneratedValue @JsonIgnore
    Long dataId;
    @Column
    LocalDate date;
    @Column
    Integer decideCnt;
    @Column
    Integer todayCnt;

    public CovidData(LocalDate date, Integer decideCnt, Integer todayCnt) {
        this.date = date;
        this.decideCnt = decideCnt;
        this.todayCnt = todayCnt;
    }

    public CovidData(LocalDate date, Integer decideCnt) {
        this.date = date;
        this.decideCnt = decideCnt;
    }

}
