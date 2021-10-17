package khuvid19.vaccinated.dto.login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfo {
    String google_name;
    String userName;
    String email;
    String picUrl;

}
