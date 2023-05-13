package GooRoom.projectgooroom.member.domain;

public enum DrinkingType {
    RARELY(0),
    SOMETIMES(1),
    USUALLY(2);

    private final int value;

    DrinkingType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
