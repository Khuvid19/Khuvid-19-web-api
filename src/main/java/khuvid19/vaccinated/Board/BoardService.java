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
    private final CommentRepository commentRepository;

    public HttpStatus saveBoard(User user, BoardInfo postBoard){
        Board board = new Board(postBoard.getTitle(), postBoard.getContent(), user);
        boardRepository.save(board);
        return HttpStatus.OK;
    }

    public Page<Board> getBoards(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date"));
        return boardRepository.findAll(pageRequest);
    }

    public HttpStatus newComment(User user,CommentInfo commentInfo) {
        Optional<Board> board = boardRepository.findById(commentInfo.getBoardId());

        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }

        Comment comment = new Comment(
                commentInfo.getContent(),
                user,
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

    public Page<Board> searchBoard(String word, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date"));
        return boardRepository.findByTitleContainingAndContentContaining(word, word, pageRequest);
    }
}
