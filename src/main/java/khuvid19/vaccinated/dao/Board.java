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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String title;
    @Column
    private String content;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private Date date;

    @Column
    private Integer comments = 0;

    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = new Date();
    }

    public Board newComments(){
        this.comments++;
        return this;
    }
}
