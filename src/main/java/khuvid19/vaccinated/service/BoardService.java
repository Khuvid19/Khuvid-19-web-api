package khuvid19.vaccinated.service;

import khuvid19.vaccinated.dao.Board;
import khuvid19.vaccinated.dao.User;
import khuvid19.vaccinated.dto.BoardDTO;
import khuvid19.vaccinated.repository.BoardRepository;
import khuvid19.vaccinated.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public HttpStatus saveBoard(BoardDTO boardDTO){
        Optional<User> getUser = userRepository.findByUserName(boardDTO.getUserName());
        if (getUser.isEmpty()) {
            return HttpStatus.UNAUTHORIZED;
        }

        Board board = new Board(boardDTO.getTitle(), boardDTO.getContent(), getUser.get());
        boardRepository.save(board);
        return HttpStatus.OK;
    }
}
