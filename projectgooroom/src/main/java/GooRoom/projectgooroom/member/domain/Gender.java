package GooRoom.projectgooroom.member.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("M", 0),
    FEMALE("F",1);

    private final String name;
    private final double value;

    Gender(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}

