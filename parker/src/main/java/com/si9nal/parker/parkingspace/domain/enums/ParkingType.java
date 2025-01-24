package com.si9nal.parker.parkingspace.domain.enums;

public enum ParkingType {
    STREET("노상"),
    OFF_STREET("노외");

    private final String korean;

    ParkingType(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}
