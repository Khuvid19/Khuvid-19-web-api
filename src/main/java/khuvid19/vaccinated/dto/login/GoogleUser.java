package khuvid19.vaccinated.dto;

import khuvid19.vaccinated.dao.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoogleUser {

    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

    public User toUser(String accessToken) {
        return new User(this.email, this.name, accessToken, this.picture);
    }
}