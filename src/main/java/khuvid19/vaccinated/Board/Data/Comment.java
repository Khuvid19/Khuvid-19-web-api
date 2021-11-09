package khuvid19.vaccinated.Board.Data;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.LoginUser.Data.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String comment;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name="board_id")
    private Board board;

    private Date date;

    public Comment(String comment, User user, Board board) {
        this.comment = comment;
        this.user = user;
        this.board = board;
        this.date = new Date();
    }
}