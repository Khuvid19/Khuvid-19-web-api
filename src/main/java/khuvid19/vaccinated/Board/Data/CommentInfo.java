package khuvid19.vaccinated.Board.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentInfo {
    String content;
    String userName;
    Date date;
    Long boardId;

    public CommentInfo(Comment comment) {
        this.content = comment.getComment();
        this.date = comment.getDate();
        this.boardId = comment.getBoard().getId();
        this.userName = comment.getUser().getUserName();
    }
}
