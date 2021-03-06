package khuvid19.vaccinated.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SideEffectType {

    LOW_FEVER("미열"), HIGH_FEVER("고열"), AREA_PAIN("부위 통증"), VOMITING("구토"),
    NAUSEA("메스꺼움"), HEADACHE("두통"), JOINT_PAIN("관절통"), MUSCLE_PAIN("근육통"),
    FATIGUE("피로감"), HIVES("두드러기"), RASH("발진"), ITCHING("가려움증"),
    SWELLING("붓기"), OTHER("기타");

    private final String koreanName;

    SideEffectType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static List<Map<String, String>> getAllTypes() {
        List<Map<String, String>> result = new ArrayList<>();
        for (SideEffectType type :
                SideEffectType.values()) {
            HashMap<String, String> element = new HashMap<>();
            element.put("code", type.toString());
            element.put("value", type.koreanName);
            result.add(element);
        }
        return result;
    }

}