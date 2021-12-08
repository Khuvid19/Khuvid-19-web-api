package khuvid19.vaccinated.SideEffects;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import khuvid19.vaccinated.Review.Data.ReviewRepository;
import khuvid19.vaccinated.SideEffects.Data.DTO.SideEffectStatistic;
import khuvid19.vaccinated.SideEffects.Data.SideEffectCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SideEffectsService {

    @Autowired
    SideEffectsRepository sideEffectsRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public SideEffectStatistic getAllSideEffectsCountsByVaccine(VaccineType vaccineType) {
        Integer vaccineCount = reviewRepository.findReviewsByVaccineEquals(vaccineType);
        List<SideEffectCount> allCounts = sideEffectsRepository.findAllByVaccineTypeOrderByCountDesc(vaccineType);
        Map<String, Integer> result = new HashMap<>();
        for (SideEffectCount count: allCounts) {
            result.put(count.getType().name(), count.getCount());
        }
        return new SideEffectStatistic(vaccineCount, result);
    }

    @Transactional
    public void addSideEffectsCount(List<SideEffectType> inputSideEffectTypes, VaccineType vaccineType) {
        List<SideEffectCount> foundSideEffectCounts = sideEffectsRepository.findSideEffectCountsByTypeInAndVaccineTypeEquals(inputSideEffectTypes, vaccineType);

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

    @Transactional
    public void subtractSideEffectCount(List<SideEffectType> oldSideEffectTypes, VaccineType oldVaccineType) {
        List<SideEffectCount> foundSideEffectCounts = sideEffectsRepository.findSideEffectCountsByTypeInAndVaccineTypeEquals(oldSideEffectTypes, oldVaccineType);
        for (SideEffectCount count : foundSideEffectCounts) {
            count.subtractCount();
        }
    }

    @Transactional
    public void updateSideEffectsCount(List<SideEffectType> oldSideEffectTypes, VaccineType oldVaccineType,
                                       List<SideEffectType> inputSideEffectTypes, VaccineType inputVaccineType) {
        subtractSideEffectCount(oldSideEffectTypes, oldVaccineType);
        addSideEffectsCount(inputSideEffectTypes, inputVaccineType);
    }

    public void removeAllData() {
        sideEffectsRepository.deleteAll();
    }

}

