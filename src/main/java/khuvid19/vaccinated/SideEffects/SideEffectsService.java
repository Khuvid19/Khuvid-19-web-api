package khuvid19.vaccinated.SideEffects;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.SideEffects.Data.SideEffectCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SideEffectsService {
    @Autowired
    SideEffectsRepository sideEffectsRepository;

    public Map<String, Integer> getAllSideEffectsCountsByVaccine(VaccineType vaccineType) {
        List<SideEffectCount> allCounts = sideEffectsRepository.findAllByVaccineTypeOrderByCountDesc(vaccineType);
        Map<String, Integer> result = new HashMap<>();
        for (SideEffectCount count: allCounts) {
            result.put(count.getType().name(), count.getCount());
        }
        return result;
    }

    @Transactional
    public void addSideEffectsCount(List<SideEffectType> inputSideEffectTypes, VaccineType vaccineType) {
        List<SideEffectCount> foundSideEffectCounts = sideEffectsRepository.findSideEffectCountsByTypeIn(inputSideEffectTypes);

        if (foundSideEffectCounts.size() != inputSideEffectTypes.size()) {
            for (SideEffectType type : inputSideEffectTypes) {
                SideEffectCount target = new SideEffectCount(type, vaccineType);
                int idx = foundSideEffectCounts.lastIndexOf(target);
                if (idx == -1) {
                    sideEffectsRepository.save(target);
                }
            }
        }

        for (SideEffectCount count : foundSideEffectCounts) {
            count.addCount();
        }

    }

}

