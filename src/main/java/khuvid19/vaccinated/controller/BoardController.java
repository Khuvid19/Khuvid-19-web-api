package khuvid19.vaccinated.controller;

import khuvid19.vaccinated.dao.Board;
import khuvid19.vaccinated.dto.BoardDTO;
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

    @GetMapping("/board")
    public Page<Board> getBoard(@RequestParam String page) {
        Integer p = Integer.parseInt(page);
        return boardService.getBoard(p-1);
    }
}
