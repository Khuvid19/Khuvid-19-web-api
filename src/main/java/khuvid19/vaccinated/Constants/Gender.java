package khuvid19.vaccinated.Constants;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    FEMALE("여성"), MALE("남성");

    private String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }

    public static Map<Gender, String> getAllTypes(){
        Map<Gender, String> result;
        result = new HashMap<>();
        for (Gender type :
                Gender.values()) {
            result.put(type, type.koreanName);
        }
        return result;
    }
}
