package khuvid19.vaccinated.SideEffects;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.SideEffects.Data.SideEffectCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SideEffectsRepository extends JpaRepository<SideEffectCount, SideEffectType> {
    List<SideEffectCount> findAll();
    List<SideEffectCount> findSideEffectCountsByTypeIn(List<SideEffectType> types);
}
