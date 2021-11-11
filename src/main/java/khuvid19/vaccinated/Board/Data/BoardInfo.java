package khuvid19.vaccinated.Board.Data;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class BoardInfo {
    Long boardId;
    String title;
    String userName;
    String content;
    Date date;
    Integer comments;
    List<CommentInfo> commentList;

    public void setCommentList(List<CommentInfo> commentList){
        this.commentList = commentList;
    }

    public BoardInfo(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.date = board.getDate();
        this.userName = board.getUser().getNickName();
        this.content = board.getContent();
        this.comments = board.getComments();
    }
}
