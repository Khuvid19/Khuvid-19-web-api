package khuvid19.vaccinated.dto.board;

import lombok.Getter;

import java.util.Date;

@Getter
public class CommentDTO {
    String content;
    String accessToken;
    Date date;
    Long boardId;
}
