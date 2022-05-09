package khuvid19.vaccinated.LoginUser.Data.DTO;


import khuvid19.vaccinated.Constants.AgeType;
import khuvid19.vaccinated.Constants.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {
    String google_name;
    String nickName;
    String email;
    String picUrl;
    AgeType age;
    Gender gender;
}
