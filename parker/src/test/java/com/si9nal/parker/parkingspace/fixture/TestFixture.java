package com.si9nal.parker.parkingspace.fixture;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;

import java.time.LocalTime;

public class TestFixture {
    public static ParkingSpace createSampleParkingSpace() {
        return ParkingSpace.builder()
                .parkingName("테스트 주차장")
                .operatingDays("월~금")
                .weekdayStartTime(LocalTime.of(9, 0))
                .weekdayEndTime(LocalTime.of(18, 0))
                .baseParkingTime(30)
                .baseParkingFee(1000)
                .address("서울시 강남구")
                .latitude(37.5665)
                .longitude(126.9780)
                .build();
    }
}