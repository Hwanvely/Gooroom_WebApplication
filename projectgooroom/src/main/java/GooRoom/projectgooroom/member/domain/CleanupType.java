package GooRoom.projectgooroom.member.domain;

public enum CleanupType {
    PER_1WEEK(0),
    PER_2WEEK(1),
    PER_MONTH(2);

    private final int value;

    CleanupType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
