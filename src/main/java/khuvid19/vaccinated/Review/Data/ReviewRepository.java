package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {
    Page<Review> findAll(Pageable pageable);
    Boolean existsReviewByUserIdAndVaccine(Long userId, VaccineType vaccine);
}

