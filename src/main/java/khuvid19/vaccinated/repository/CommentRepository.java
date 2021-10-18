package khuvid19.vaccinated.repository;

import khuvid19.vaccinated.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
