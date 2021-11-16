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
public class ReviewCard {
    private Gender authorGender;
    private VaccineType vaccine;
    private AgeType authorAge;
    private Date createdDate;
    private Date inoculatedDate;
    private List<SideEffectType> sideEffects;
    private Boolean haveDisease;
    private String diseaseDisc;
    private String detailDisc;
}
