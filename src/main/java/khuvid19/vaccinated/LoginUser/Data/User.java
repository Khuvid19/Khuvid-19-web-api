package khuvid19.vaccinated.LoginUser.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String accessToken;
    @Column
    private String picUrl;
    @Column
    private String nickName;
    private String jwtToken;

    public User(String email, String name, String accessToken, String picUrl) {
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
        this.picUrl = picUrl;
    }

    public User setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public User setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    public UserInfo toUserInfo(){
        return new UserInfo(this.name, this.nickName, this.email, this.picUrl);
    }
}