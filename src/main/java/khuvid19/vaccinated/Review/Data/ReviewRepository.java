package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findAll();
    Boolean existsReviewByUserIdAndVaccine(Long userId, VaccineType vaccine);
}

