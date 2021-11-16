package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReviewRepository extends PagingAndSortingRepository<Review, String>, JpaSpecificationExecutor<Review> {
    Page<Review> findAll(Pageable pageable);

    Boolean existsReviewsByAuthor_IdAndVaccine(Long userId, VaccineType vaccine);
    List<Review> findAllByAuthor_Id(Long id);
}

