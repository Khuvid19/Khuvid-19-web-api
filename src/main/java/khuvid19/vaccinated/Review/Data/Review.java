package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Review {

    @Id @GeneratedValue
    Long id;

    Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date inoculatedDate;

    VaccineType vaccine;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    List<SideEffectType> sideEffects;

    Boolean haveDisease;
    String diseaseDisc;

    String detailDisc;
}
