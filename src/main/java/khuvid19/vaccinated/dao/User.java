package khuvid19.vaccinated.dao;

import khuvid19.vaccinated.dto.login.UserInfo;
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
    private String userName;

    public User(String email, String name, String accessToken, String picUrl) {
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
        this.picUrl = picUrl;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public UserInfo toUserInfo(){
        return new UserInfo(this.getName(), this.getUserName(), this.getEmail(), this.getPicUrl());
    }
}