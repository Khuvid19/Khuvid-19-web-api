package khuvid19.vaccinated.Review.Data.DTO;

import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewCard {
    private String authorGender;
    private VaccineType vaccine;
}
