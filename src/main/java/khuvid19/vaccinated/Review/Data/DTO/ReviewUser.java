package khuvid19.vaccinated.Review.Data.DTO;

import khuvid19.vaccinated.Constants.ReviewType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUser {

    Long id;
    String Name;
    ReviewType personType;

}
