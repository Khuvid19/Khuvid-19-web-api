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
    Long commentId;

    public CommentInfo(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getComment();
        this.date = comment.getDate();
        this.userName = comment.getUser().getNickName();
    }
}
