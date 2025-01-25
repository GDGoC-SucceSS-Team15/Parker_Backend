package com.si9nal.parker.parkingspace.domain.enums;

public enum FeeType {
    FREE("무료"),
    PAID("유료");

    private final String korean;

    FeeType(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}