package khuvid19.vaccinated.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Gender {
    FEMALE("여성"), MALE("남성");

    private String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    public static List<Map> getAllTypes() {
        List gender = new ArrayList();
        Map<String, String> result;
        for (Gender type :
                Gender.values()) {
            result = new HashMap<>();
            result.put("code", type.toString());
            result.put("value", type.koreanName);
            gender.add(result);
        }
        return gender;
    }
}
