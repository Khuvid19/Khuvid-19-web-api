package khuvid19.vaccinated.SimpleReview.Data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimpleReviewRepository extends JpaRepository<SimpleReview, String> {
    List<SimpleReview> findAll();
}

