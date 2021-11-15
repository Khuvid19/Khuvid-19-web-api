package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.*;
import khuvid19.vaccinated.LoginUser.Data.User;
import khuvid19.vaccinated.LoginUser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public HttpStatus saveBoard(User user, BoardPost postBoard){
        Board board = new Board(postBoard.getTitle(), postBoard.getContent(), user);
        boardRepository.save(board);
        return HttpStatus.OK;
    }

    public HttpStatus reviseBoard(Long boardId, BoardPost post, User user) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }
        if (board.get().getUser().equals(user)) {
            Board revised = board.get();
            revised.setTitle(post.getTitle());
            revised.setContent(post.getContent());
            boardRepository.save(revised);
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.UNAUTHORIZED;
        }
    }


    public HttpStatus deleteBoard(Long boardId, User user) {
        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isEmpty()){
            return HttpStatus.GONE;
        }
        if (board.get().getUser().equals(user)) {
            boardRepository.delete(board.get());
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.UNAUTHORIZED;
        }
    }

    public Page<Board> getBoards(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "date"));
        return boardRepository.findAll(pageRequest);
    }

    public HttpStatus newComment(User user,CommentPost commentPost) {
        Optional<Board> board = boardRepository.findById(commentPost.getBoardId());

        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }

        Comment comment = new Comment(
                commentPost.getContent(),
                user,
                board.get()
        );

        commentRepository.save(comment);
        boardRepository.save(board.get().newComments());

        return HttpStatus.OK;
    }

    public HttpStatus reviseComment(CommentPost commentPost, User user) {
        Optional<Board> board = boardRepository.findById(commentPost.getBoardId());
        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }
        Optional<Comment> comment = commentRepository.findById(commentPost.getCommentId());
        if (comment.isEmpty()) {
            return HttpStatus.GONE;
        }
        if (comment.get().getUser().equals(user)) {
            Comment revised = comment.get();
            revised.setComment(commentPost.getContent());
            commentRepository.save(revised);
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus deleteComment(Long commentId, Long boardId, User user) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        Optional<Board> board = boardRepository.findById(boardId);
        if (comment.isEmpty()) {
            return HttpStatus.GONE;
        }
        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }
        if (comment.get().getUser().equals(user)) {
            commentRepository.delete(comment.get());
            boardRepository.save(board.get().deleteComments());
            return HttpStatus.OK;
        }
        return HttpStatus.UNAUTHORIZED;
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
