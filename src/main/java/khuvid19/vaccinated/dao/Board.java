package khuvid19.vaccinated.dao;

import khuvid19.vaccinated.dto.login.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id
    @Column
    @GeneratedValue
    private Long Id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int likes = 0;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    private Date date;


    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = new Date();
    }
}
