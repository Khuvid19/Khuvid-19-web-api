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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity newBoard(@RequestBody PostBoard postBoard, @ApiIgnore@AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.saveBoard(securityUser.getUser(),postBoard);
    }

    @PutMapping()
    public ResponseEntity reviseBoard(@RequestBody ReviseBoard post, @ApiIgnore@AuthenticationPrincipal SecurityUser securityUser) {
        User user = securityUser.getUser();
        return boardService.reviseBoard(post, user);
    }

    @DeleteMapping()
    public ResponseEntity deleteBoard(@RequestBody ReviseBoard board, @ApiIgnore@AuthenticationPrincipal SecurityUser user) {
        return boardService.deleteBoard(board.getBoardId(), user.getUser());
    }

    @PostMapping("/comment")
    public ResponseEntity newComment(@RequestBody PostComment commentInfo, @ApiIgnore@AuthenticationPrincipal SecurityUser securityUser) {
        return boardService.newComment(securityUser.getUser(), commentInfo);
    }

    @PutMapping("/comment")
    public ResponseEntity reviseComment(@RequestBody PostComment postComment,@ApiIgnore @AuthenticationPrincipal SecurityUser user) {
        return boardService.reviseComment(postComment, user.getUser());
    }

    @DeleteMapping("/comment")
    public ResponseEntity deleteComment(@RequestBody PostComment postComment, @ApiIgnore@AuthenticationPrincipal SecurityUser user) {
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
        return boardService.searchBoard(search, page);
    }

    @GetMapping("/user")
    public List<Board> getMyBoards(@ApiIgnore @AuthenticationPrincipal SecurityUser securityUser) {
        return boardRepository.findByUser(securityUser.getUser());
    }

}
