package khuvid19.vaccinated.Constants;

import java.util.HashMap;
import java.util.Map;

public enum AgeType {
    UNDER_TEEN("10대 이하"),TEENAGER("10대"), TWENTIES("20대"),
    THIRTIES("30대"), FORTIES("40대"), FIFTIES("50대"),
    SIXTIES("60대"), OVER_SEVENTY("70대 이상");

    private final String koreanAge;

    AgeType(String koreanAge) {
        this.koreanAge = koreanAge;
    }

    public static Map<AgeType, String> getAllTypes(){
        Map<AgeType, String> result;
        result = new HashMap<>();
        for (AgeType type : AgeType.values()){
            result.put(type, type.koreanAge);
        }
        return result;
    }
}
