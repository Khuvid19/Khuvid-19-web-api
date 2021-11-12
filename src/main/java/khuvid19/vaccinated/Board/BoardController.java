package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.Board.Data.BoardInfo;
import khuvid19.vaccinated.Board.Data.Comment;
import khuvid19.vaccinated.Board.Data.CommentInfo;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Security;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public HttpStatus newBoard(@RequestBody BoardInfo postBoard, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.saveBoard(securityUser.getUser(),postBoard);
    }

    @PostMapping("/board/comment")
    public HttpStatus newComment(@RequestBody CommentInfo commentInfo, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.newComment(securityUser.getUser(), commentInfo);
    }

    @GetMapping("/board/list")
    public Page<Board> getBoardList(@RequestParam String page) {
        Integer p = Integer.parseInt(page);
        return boardService.getBoards(p-1);
    }

    @GetMapping("/board/detail")
    public BoardInfo getBoard(@RequestParam Long boardId) {
        return boardService.detailBoard(boardId);
    }
}
