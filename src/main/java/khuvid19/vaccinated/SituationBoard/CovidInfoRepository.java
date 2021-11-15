package khuvid19.vaccinated.SituationBoard;

import khuvid19.vaccinated.SituationBoard.Data.CovidData;
import khuvid19.vaccinated.SituationBoard.Data.CovidInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CovidInfoRepository extends JpaRepository<CovidInfo, Long> {
    public Optional<CovidInfo> findByDate(LocalDate date);
}
