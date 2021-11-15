package khuvid19.vaccinated.Constants;

public enum AgeType {
    UNDER_TEEN("10대 이하"),TEENAGER("10대"), TWENTIES("20대"),
    THIRTIES("30대"), FORTIES("40대"), FIFTIES("50대"),
    SIXTIES("60대"), OVER_SEVENTY("70대 이상");

    private final String koreanAge;

    AgeType(String koreanAge) {
        this.koreanAge = koreanAge;
    }
}
