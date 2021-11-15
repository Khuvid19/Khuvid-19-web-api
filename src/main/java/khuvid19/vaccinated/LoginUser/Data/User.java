package khuvid19.vaccinated.LoginUser.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Setter
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
    @Column
    private String gender;
    @Column
    private Date birthday;
    @Transient
    private String jwtToken;

    public User(String email, String name, String accessToken, String picUrl) {
        this.email = email;
        this.name = name;
        this.accessToken = accessToken;
        this.picUrl = picUrl;
    }


    public UserInfo toUserInfo(){
        return new UserInfo(this.name, this.nickName, this.email, this.picUrl, this.birthday, this.gender);
    }
}