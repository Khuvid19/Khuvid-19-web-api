package khuvid19.vaccinated.Constants;

import java.util.HashMap;
import java.util.Map;

public enum VaccineType {
    ANSEN("얀센"), ANSEN_BOOST("얀센 부스터샷"),
    PFIZER_FIRST("화이자 1차"), PFIZER_SECOND("화이자 2차"),
    MODERNA_FIRST("모더나 1차"), MODERNA_SECOND("모더나 2차"),
    ASTRAZENEKA_FIRST("아스트라제네카 1차"), ASTRAZENEKA_SECOND("아스트라제네카 2차");

    private final String koreanName;

    VaccineType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Map<VaccineType, String> getAllTypes() {
        Map<VaccineType, String> result;
        result = new HashMap<>();
        for (VaccineType type :
                VaccineType.values()) {
            result.put(type, type.koreanName);
        }
        return result;
    }
}

