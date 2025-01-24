package com.si9nal.parker.parkingspace.domain.enums;

public enum ParkingUsage {
    PUBLIC("공영"),
    PRIVATE("민영");

    private final String korean;

    ParkingUsage(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}