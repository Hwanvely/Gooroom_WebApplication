package GooRoom.projectgooroom.member.domain;

public enum DrinkingType {
    RARELY(0),
    SOMETIMES(1),
    USUALLY(2);

    private final double value;

    DrinkingType(int value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
