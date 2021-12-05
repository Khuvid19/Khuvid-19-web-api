package khuvid19.vaccinated.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum VaccineType {
    PFIZER_FIRST("화이자 1차"), PFIZER_SECOND("화이자 2차"),
    MODERNA_FIRST("모더나 1차"), MODERNA_SECOND("모더나 2차"),
    AZ_FIRST("AZ 1차"), AZ_SECOND("AZ 2차"),
    ANSEN("얀센"), ANSEN_BOOST("얀센 부스터샷");

    private final String koreanName;

    VaccineType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static List<Map<String, String>> getAllTypes() {
        List<Map<String, String>> result = new ArrayList<>();
        for (VaccineType type :
                VaccineType.values()) {
            Map<String, String> element = new HashMap<>();
            element.put("code", type.toString());
            element.put("value", type.koreanName);
            result.add(element);
        }
        return result;
    }
}

