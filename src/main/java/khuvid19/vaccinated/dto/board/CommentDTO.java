package khuvid19.vaccinated.dto.board;

import khuvid19.vaccinated.dao.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentDTO {
    String content;
    String userName;
    Date date;
    Long boardId;

    public CommentDTO(Comment comment) {
        this.content = comment.getComment();
        this.date = comment.getDate();
        this.boardId = comment.getBoard().getId();
        this.userName = comment.getUser().getUserName();
    }
}
