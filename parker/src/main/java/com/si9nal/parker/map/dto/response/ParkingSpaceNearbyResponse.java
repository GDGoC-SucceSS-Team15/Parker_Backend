package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ParkingSpaceNearbyResponse {
    private Long id;
    private String parkingName;
    private String address;

    private String weekdayTime;
    private String saturdayTime;
    private String holidayTime;

    private String baseParkingTime;
    private String baseParkingFee;

    private String distance;

    public static ParkingSpaceNearbyResponse of(ParkingSpace parkingSpace, Double distance) {
        return ParkingSpaceNearbyResponse.builder()
                .id(parkingSpace.getId())
                .parkingName(parkingSpace.getParkingName())
                .address(parkingSpace.getAddress())

                .weekdayTime(formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))

                .baseParkingFee(parkingSpace.getBaseParkingFee() != null ? parkingSpace.getBaseParkingFee().toString() : "정보 없음")
                .baseParkingTime(parkingSpace.getBaseParkingTime() != null ? parkingSpace.getBaseParkingTime().toString() : "정보 없음")
                .distance(formatDistance(distance))
                .build();
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static String formatTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return "정보 없음";
        }
        return String.format("%s ~ %s", startTime.format(TIME_FORMATTER), endTime.format(TIME_FORMATTER));
    }

    private static String formatDistance(Double distance) {
        if (distance == null) {
            return "정보 없음";
        }
        return String.format("%.1fkm", distance);  // 소수점 첫째 자리까지 포맷
    }
}