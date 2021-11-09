package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.Board.Data.Comment;
import khuvid19.vaccinated.Board.Data.BoardInfo;
import khuvid19.vaccinated.Board.Data.CommentInfo;
import khuvid19.vaccinated.Board.Data.CommentRepository;
import khuvid19.vaccinated.LoginUser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public HttpStatus saveBoard(BoardInfo postBoard){
        Optional<User> getUser = userRepository.findByUserName(postBoard.getUserName());
        if (getUser.isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }

        Board board = new Board(postBoard.getTitle(), postBoard.getContent(), getUser.get());
        boardRepository.save(board);
        return HttpStatus.OK;
    }

    public Page<Board> getBoards(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date"));
        return boardRepository.findAll(pageRequest);
    }

    public HttpStatus newComment(CommentInfo commentInfo) {
        Optional<User> user = userRepository.findByUserName(commentInfo.getUserName());
        Optional<Board> board = boardRepository.findById(commentInfo.getBoardId());

        if (user.isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }

        Comment comment = new Comment(
                commentInfo.getContent(),
                user.get(),
                board.get()
        );

        commentRepository.save(comment);
        boardRepository.save(board.get().newComments());

        return HttpStatus.OK;
    }

    public BoardInfo detailBoard(Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()) {
            return null;
        }
        BoardInfo boardInfo = new BoardInfo(board.get());
        List<Comment> byBoardId = commentRepository.findByBoard(board.get());
        List<CommentInfo> commentInfoList = new ArrayList<>();
        byBoardId.forEach(comment -> {
            commentInfoList.add(new CommentInfo(comment));
        });
        boardInfo.setCommentList(commentInfoList);

        return boardInfo;
    }

}
