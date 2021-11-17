package khuvid19.vaccinated.SituationBoard;

import khuvid19.vaccinated.SituationBoard.Data.CovidData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CovidRepository extends JpaRepository<CovidData, Long> {
    Optional<CovidData> findByDate(LocalDate date);
}
