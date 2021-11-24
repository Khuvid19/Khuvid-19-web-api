package khuvid19.vaccinated.Review.Data.DTO;

import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class ReviewFilter {
    List<VaccineType> vaccines;
    List<Gender> authorGenders;
    List<AgeType> authorAges;
    Boolean haveDisease;
    String detailDisc;
    Date startInoculated;
    Date endInoculated;
    List<SideEffectType> sideEffects;
}
