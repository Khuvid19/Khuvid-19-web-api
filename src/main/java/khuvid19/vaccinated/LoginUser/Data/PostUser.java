package khuvid19.vaccinated.LoginUser.Data;

import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import lombok.Getter;

@Getter
public class PostUser {
    Gender gender;
    AgeType age;
    String nickName;
}
