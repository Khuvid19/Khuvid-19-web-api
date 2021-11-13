package khuvid19.vaccinated.Board.Data;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.Board.Data.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBoard(Board board);
}
