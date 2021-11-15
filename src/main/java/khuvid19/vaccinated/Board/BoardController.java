package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.*;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.LoginUser.Data.User;
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
    public HttpStatus newBoard(@RequestBody BoardPost postBoard, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.saveBoard(securityUser.getUser(),postBoard);
    }

    @PutMapping()
    public HttpStatus reviseBoard(@RequestParam Long boardId, @RequestBody BoardPost post, @AuthenticationPrincipal SecurityUser securityUser) {
        User user = securityUser.getUser();
        return boardService.reviseBoard(boardId, post, user);
    }

    @DeleteMapping()
    public HttpStatus deleteBoard(@RequestParam Long boardId, @AuthenticationPrincipal SecurityUser user) {
        return boardService.deleteBoard(boardId, user.getUser());
    }

    @PostMapping("/comment")
    public HttpStatus newComment(@RequestBody CommentPost commentInfo, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.newComment(securityUser.getUser(), commentInfo);
    }

    @PutMapping("/comment")
    public HttpStatus reviseComment(@RequestParam CommentPost commentPost, @AuthenticationPrincipal SecurityUser user) {
        return boardService.reviseComment(commentPost, user.getUser());
    }

    @DeleteMapping("/comment")
    public HttpStatus deleteComment(@RequestParam Long commentId, @AuthenticationPrincipal SecurityUser user) {
        return boardService.deleteComment(commentId, user.getUser());
    }

    @GetMapping
    public Page<Board> getBoardList(@RequestParam Integer page) {
        return boardService.getBoards(page);
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
