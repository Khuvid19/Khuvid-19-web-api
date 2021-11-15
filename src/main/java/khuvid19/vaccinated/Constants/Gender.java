package khuvid19.vaccinated.Constants;

public enum Gender {
    FEMALE("여성"), MALE("남성");

    private String koreanName;

    Gender(String koreanName) {
        this.koreanName = koreanName;
    }
}
