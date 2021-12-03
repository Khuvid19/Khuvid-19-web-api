package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board.Board;
import khuvid19.vaccinated.Board.Data.Board.BoardInfo;
import khuvid19.vaccinated.Board.Data.Board.PostBoard;
import khuvid19.vaccinated.Board.Data.Board.ReviseBoard;
import khuvid19.vaccinated.Board.Data.Comment.Comment;
import khuvid19.vaccinated.Board.Data.Comment.CommentInfo;
import khuvid19.vaccinated.Board.Data.Comment.PostComment;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity saveBoard(User user, PostBoard postBoard){
        Optional<User> userById = userRepository.findById(user.getId());
        if (userById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Board board = new Board(postBoard.getTitle(), postBoard.getContent(),userById.get());
        boardRepository.save(board);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity reviseBoard(ReviseBoard newBoard, User user) {
        Optional<Board> board = boardRepository.findById(newBoard.getBoardId());
        if (board.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        Board savedBoard = board.get();

        if (!savedBoard.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Board revised = board.get();
        revised.setTitle(newBoard.getTitle());
        revised.setContent(newBoard.getContent());
        boardRepository.save(revised);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public ResponseEntity deleteBoard(Long boardId, User user) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()){
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        Board savedBoard = board.get();
        if (!savedBoard.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        boardRepository.delete(savedBoard);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public Page<Board> getBoards(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date"));
        return boardRepository.findAll(pageRequest);
    }

    public ResponseEntity newComment(User user, PostComment postComment) {
        Optional<Board> board = boardRepository.findById(postComment.getBoardId());
        if (board.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        Comment comment = new Comment(
                postComment.getContent(),
                user,
                board.get()
        );

        commentRepository.save(comment);
        boardRepository.save(board.get().newComments());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity reviseComment(PostComment postComment, User user) {
        Optional<Board> board = boardRepository.findById(postComment.getBoardId());
        if (board.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        Optional<Comment> comment = commentRepository.findById(postComment.getCommentId());
        if (comment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        if (!comment.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Comment revised = comment.get();
        revised.setComment(postComment.getContent());
        commentRepository.save(revised);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity deleteComment(Long commentId, Long boardId, User user) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<Board> board = boardRepository.findById(boardId);
        if (comment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (board.isEmpty()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        if (!comment.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentRepository.deleteById(comment.get().getCommentId());
        boardRepository.save(board.get().deleteComments());
        return ResponseEntity.status(HttpStatus.OK).build();
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
        return boardRepository.findByTitleContainingOrContentContaining(word, word, pageRequest);
    }
}
