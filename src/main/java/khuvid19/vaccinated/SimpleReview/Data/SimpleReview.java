package khuvid19.vaccinated.SimpleReview.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class SimpleReview {

    @Id @GeneratedValue
    Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date inoculatedDate;

    VaccineType vaccine;

    @ElementCollection
    List<SideEffectType> sideEffectTypes;

    Boolean haveDisease;

    String diseaseDisc;
}
