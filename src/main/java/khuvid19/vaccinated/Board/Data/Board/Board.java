package khuvid19.vaccinated.Board.Data.Board;

import khuvid19.vaccinated.LoginUser.Data.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String title;
    @Column(length=5000)
    private String content;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private LocalDateTime date;

    @Column
    private Integer comments = 0;

    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    public Board newComments(){
        this.comments++;
        return this;
    }

    public Board deleteComments(){
        this.comments = this.comments-1;
        return this;
    }
}
