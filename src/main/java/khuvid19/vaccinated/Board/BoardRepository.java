package khuvid19.vaccinated.Board;

import khuvid19.vaccinated.Board.Data.Board;
import khuvid19.vaccinated.LoginUser.Data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {
    Page<Board> findAll(Pageable pageable);
    Page<Board> findByTitleContainingAndContentContaining(String title, String content, Pageable pageable);

    List<Board> findByUser(User user);
}
