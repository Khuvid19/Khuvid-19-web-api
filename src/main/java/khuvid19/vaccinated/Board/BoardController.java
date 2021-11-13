package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.Board.Data.BoardInfo;
import khuvid19.vaccinated.Board.Data.CommentInfo;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public HttpStatus newBoard(@RequestBody BoardInfo postBoard, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.saveBoard(securityUser.getUser(),postBoard);
    }

    @PostMapping("/comment")
    public HttpStatus newComment(@RequestBody CommentInfo commentInfo, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.newComment(securityUser.getUser(), commentInfo);
    }

    @GetMapping
    public Page<Board> getBoardList(@RequestParam Integer page) {
        return boardService.getBoards(page-1);
    }

    @GetMapping("/detail")
    public BoardInfo getBoard(@RequestParam Long boardId) {
        return boardService.detailBoard(boardId);
    }

    @GetMapping("/list")
    public Page<Board> searchBoard(@RequestParam String search, @RequestParam Integer page) {
        return boardService.searchBoard(search, page-1);
    }
}
