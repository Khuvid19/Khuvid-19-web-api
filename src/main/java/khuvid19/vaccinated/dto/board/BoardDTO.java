package khuvid19.vaccinated.dto.board;

import khuvid19.vaccinated.dao.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class BoardDTO {
    Long boardId;
    String title;
    String userName;
    String content;
    Date date;
    Integer comments;
    List<CommentDTO> commentList;

    public void setCommentList(List<CommentDTO> commentList){
        this.commentList = commentList;
    }

    public BoardDTO(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.date = board.getDate();
        this.userName = board.getUser().getUserName();
        this.content = board.getContent();
        this.comments = board.getComments();
    }
}
