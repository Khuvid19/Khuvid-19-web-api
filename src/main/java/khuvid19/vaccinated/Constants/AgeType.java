package khuvid19.vaccinated.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AgeType {
    TEENAGER("10대"), TWENTIES("20대"),
    THIRTIES("30대"), FORTIES("40대"), FIFTIES("50대"),
    SIXTIES("60대"), OVER_SEVENTY("70대 이상");

    private final String koreanAge;

    AgeType(String koreanAge) {
        this.koreanAge = koreanAge;
    }

    public static List getAllTypes(){
        List ageType = new ArrayList();
        Map<String, String> result;
        for (AgeType type : AgeType.values()){
            result = new HashMap<>();
            result.put("code", type.toString());
            result.put("value", type.koreanAge);
            ageType.add(result);
        }
        return ageType;
    }
}
