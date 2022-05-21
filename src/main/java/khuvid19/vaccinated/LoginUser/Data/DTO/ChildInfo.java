package khuvid19.vaccinated.LoginUser.Data.DTO;

import khuvid19.vaccinated.Constants.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildInfo {
    Long id;
    String name;
    Gender gender;
    //Integer age
}
