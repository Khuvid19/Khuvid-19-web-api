package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.Board.Data.BoardInfo;
import khuvid19.vaccinated.Board.Data.CommentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public HttpStatus newBoard(@RequestBody BoardInfo postBoard) {
        return boardService.saveBoard(postBoard);
    }

    @PostMapping("/board/comment")
    public HttpStatus newComment(@RequestBody CommentInfo comment) {
        return boardService.newComment(comment);
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
