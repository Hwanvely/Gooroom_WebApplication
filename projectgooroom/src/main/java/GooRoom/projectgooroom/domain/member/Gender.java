package GooRoom.projectgooroom.domain.member;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}

