package khuvid19.vaccinated.controller;

import khuvid19.vaccinated.dao.Board;
import khuvid19.vaccinated.dao.Comment;
import khuvid19.vaccinated.dto.board.BoardDTO;
import khuvid19.vaccinated.dto.board.CommentDTO;
import khuvid19.vaccinated.service.BoardService;
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
    public HttpStatus newBoard(@RequestBody BoardDTO postBoard) {
        return boardService.saveBoard(postBoard);
    }

    @PostMapping("/board/comment")
    public HttpStatus newComment(@RequestBody CommentDTO comment) {
        return boardService.newComment(comment);
    }

    @GetMapping("/board/list")
    public Page<Board> getBoardList(@RequestParam String page) {
        Integer p = Integer.parseInt(page);
        return boardService.getBoards(p-1);
    }

    @GetMapping("/board/detail")
    public BoardDTO getBoard(@RequestParam Long boardId) {
        return boardService.detailBoard(boardId);
    }
}
