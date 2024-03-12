package kernel.jdon.moduledomain.member.domain;

import java.util.Arrays;

public enum Gender {

    MALE("남성"),
    FEMALE("여성");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public static Gender ofType(String gender) {
        return Arrays.stream(Gender.values())
            .filter(e -> e.getGender().equals(gender))
            .findAny()
            .orElse(null);
    }

    public String getGender() {
        return gender;
    }
}
