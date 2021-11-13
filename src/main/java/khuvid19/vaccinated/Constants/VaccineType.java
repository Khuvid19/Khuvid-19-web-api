package khuvid19.vaccinated.Constants;

public enum VaccineType {
    ANSEN("얀센"), ANSEN_BOOST("얀센 부스터샷"),
    PFIZER_FIRST("화이자 1차"), PFIZER_SECOND("화이자 2차"),
    MODERNA_FIRST("모더나 1차"), MODERNA_SECOND("모더나 2차"),
    ASTRAZENEKA_FIRST("아스트라제네카 1차"), ASTRAZENEKA_SECOND("아스트라제네카 1차");

    private final String koreanName;

    VaccineType(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}

