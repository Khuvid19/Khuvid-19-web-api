package khuvid19.vaccinated.LoginUser.Data;


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
    Date birthday;
    String gender;
}
