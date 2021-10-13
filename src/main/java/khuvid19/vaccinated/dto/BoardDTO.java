package khuvid19.vaccinated.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {
    Long boardId;
    String title;
    String userName;
    String content;
    Long like;

    public void liked(){
        this.like = this.like+1;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
