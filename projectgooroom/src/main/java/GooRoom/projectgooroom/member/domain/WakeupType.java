package GooRoom.projectgooroom.member.domain;

public enum WakeupType {
    DAWN(0),
    MORNING(1),
    AFTERNOON(2);

    private final int value;

    WakeupType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
