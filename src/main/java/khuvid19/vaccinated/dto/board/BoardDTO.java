package khuvid19.vaccinated.dto.board;

import lombok.Getter;

import java.util.Date;

@Getter
public class BoardDTO {
    Long boardId;
    String title;
    String userName;
    String content;
    Date date;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
