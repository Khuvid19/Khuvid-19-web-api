package khuvid19.vaccinated.dao;

import khuvid19.vaccinated.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
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
        Random random = new Random();
        this.id = random.nextLong();
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
        this.picUrl = picUrl;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserInfo toUserInfo(){
        return new UserInfo(this.getName(), this.getUserName(), this.getEmail(), this.getPicUrl());
    }
}
