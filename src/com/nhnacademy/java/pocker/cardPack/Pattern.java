package com.nhnacademy.java.pocker.cardPack;

public enum Pattern {
    SPACE(0), DIAMOND(1), HEART(2), CLUB(3);

    private final int value;

    Pattern(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
