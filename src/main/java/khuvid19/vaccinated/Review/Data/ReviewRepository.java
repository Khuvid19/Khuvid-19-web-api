package khuvid19.vaccinated.Review.Data;

import khuvid19.vaccinated.Constants.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    Page<Review> findAll(Pageable pageable);

    Boolean existsReviewsByAuthor_IdAndVaccine(Long userId, VaccineType vaccine);

    Boolean existsByReviewTargetIdAndVaccine(Long targetId, VaccineType vaccineType);
    List<Review> findAllByAuthor_Id(Long id);

    Integer countReviewsByVaccineEquals(VaccineType vaccineType);
}

