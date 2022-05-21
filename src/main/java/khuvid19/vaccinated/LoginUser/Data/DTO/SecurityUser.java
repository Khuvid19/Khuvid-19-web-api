package khuvid19.vaccinated.LoginUser.Data.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";

    private khuvid19.vaccinated.LoginUser.Data.User user;

    public SecurityUser(khuvid19.vaccinated.LoginUser.Data.User user){
        super(String.valueOf(user.getId()), user.getName(),makeGrantedAuthority("user"));
        this.user = user;
    }
    private static List<GrantedAuthority> makeGrantedAuthority(String userType){

        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + userType));

        return list;

    }
}