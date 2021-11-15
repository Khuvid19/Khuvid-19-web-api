package khuvid19.vaccinated.Review.Data.DTO;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class ReviewFilter {
    VaccineType vaccine;
    String gender;
    List<SideEffectType> sideEffects;
    Boolean haveDisease;
    String diseaseDisc;
    String detailDisc;
    Date startInoculated;
    Date endInoculated;
}
