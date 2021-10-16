package khuvid19.vaccinated.SimpleReview.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class SimpleReview {

    @Id @GeneratedValue
    Long id;

    VaccineType vaccine;
    Date inoculatedDate;
    Boolean haveDisease;
    String diseaseDisc;
}
