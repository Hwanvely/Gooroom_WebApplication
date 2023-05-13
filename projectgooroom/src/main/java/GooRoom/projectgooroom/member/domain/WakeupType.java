package GooRoom.projectgooroom.member.domain;

public enum WakeupType {
    DAWN(0),
    MORNING(1),
    AFTERNOON(2);

    private final double value;

    WakeupType(int value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
