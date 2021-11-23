package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board.Board;
import khuvid19.vaccinated.Board.Data.Board.BoardInfo;
import khuvid19.vaccinated.Board.Data.Board.PostBoard;
import khuvid19.vaccinated.Board.Data.Board.ReviseBoard;
import khuvid19.vaccinated.Board.Data.Comment.PostComment;
import khuvid19.vaccinated.LoginUser.Data.SecurityUser;
import khuvid19.vaccinated.LoginUser.Data.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @PostMapping
    public HttpStatus newBoard(@RequestBody PostBoard postBoard, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.saveBoard(securityUser.getUser(),postBoard);
    }

    @PutMapping()
    public HttpStatus reviseBoard(@RequestBody ReviseBoard post, @AuthenticationPrincipal SecurityUser securityUser) {
        User user = securityUser.getUser();
        return boardService.reviseBoard(post, user);
    }

    @DeleteMapping()
    public HttpStatus deleteBoard(@RequestBody ReviseBoard board, @AuthenticationPrincipal SecurityUser user) {
        return boardService.deleteBoard(board.getBoardId(), user.getUser());
    }

    @PostMapping("/comment")
    public HttpStatus newComment(@RequestBody PostComment commentInfo, @AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.newComment(securityUser.getUser(), commentInfo);
    }

    @PutMapping("/comment")
    public HttpStatus reviseComment(@RequestBody PostComment postComment, @AuthenticationPrincipal SecurityUser user) {
        return boardService.reviseComment(postComment, user.getUser());
    }

    @DeleteMapping("/comment")
    public HttpStatus deleteComment(@RequestBody PostComment postComment, @AuthenticationPrincipal SecurityUser user) {
        return boardService.deleteComment(postComment.getCommentId(), postComment.getBoardId(), user.getUser());
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

    @GetMapping("/user")
    public List<Board> getMyBoards(@ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        return boardRepository.findByUser(securityUser.getUser());
    }

}
