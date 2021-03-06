package khuvid19.vaccinated.Board.Data.Comment;

import khuvid19.vaccinated.Board.Data.Comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentInfo {
    String content;
    String userName;
    LocalDateTime date;
    Long commentId;

    public CommentInfo(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getComment();
        this.date = comment.getDate();
        this.userName = comment.getUser().getNickName();
    }
}
