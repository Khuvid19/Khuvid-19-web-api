package khuvid19.vaccinated.Review.Data.DTO;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ReviewInput {

    Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date inoculatedDate;

    VaccineType vaccine;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    List<SideEffectType> sideEffects;

    String etcSideEffect;

    Boolean haveDisease;
    String diseaseDisc;

    String detailDisc;
}
