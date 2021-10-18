package khuvid19.vaccinated.service;

import khuvid19.vaccinated.dao.Board;
import khuvid19.vaccinated.dao.Comment;
import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.dto.board.BoardDTO;
import khuvid19.vaccinated.dto.board.CommentDTO;
import khuvid19.vaccinated.repository.BoardRepository;
import khuvid19.vaccinated.repository.CommentRepository;
import khuvid19.vaccinated.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public HttpStatus saveBoard(BoardDTO postBoard){
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

    public HttpStatus newComment(CommentDTO commentDTO) {
        Optional<User> user = userRepository.findByAccessToken(commentDTO.getAccessToken());
        Optional<Board> board = boardRepository.findById(commentDTO.getBoardId());

        if (user.isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }
        if (board.isEmpty()) {
            return HttpStatus.GONE;
        }

        Comment comment = new Comment(
                commentDTO.getContent(),
                user.get(),
                board.get()
        );

        commentRepository.save(comment);
        boardRepository.save(board.get().newComments());

        return HttpStatus.OK;
    }

}
