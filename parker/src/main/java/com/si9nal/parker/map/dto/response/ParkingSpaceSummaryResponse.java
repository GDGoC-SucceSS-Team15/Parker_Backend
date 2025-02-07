package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 지도 메인 화면에서 주차장 하나를 조회했을 때 나오는 간략한 상세정보 DTO
 */
@Getter
@Builder
public class ParkingSpaceSummaryResponse {
    private Long id;
    private String parkingName;

    private String weekdayTime;
    private String saturdayTime;
    private String holidayTime;

    private String baseParkingFee;

    private Double latitude;
    private Double longitude;

    private boolean isBookmarked;

    public static ParkingSpaceSummaryResponse of(ParkingSpace parkingSpace, boolean isBookmarked) {
        return ParkingSpaceSummaryResponse.builder()
                .id(parkingSpace.getId())
                .parkingName(parkingSpace.getParkingName())
                .weekdayTime(formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))
                .baseParkingFee(parkingSpace.getBaseParkingFee() != null ? parkingSpace.getBaseParkingFee().toString() : "정보 없음")
                .latitude(parkingSpace.getLatitude())
                .longitude(parkingSpace.getLongitude())
                .isBookmarked(isBookmarked)
                .build();
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static String formatTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return "정보 없음";
        }
        return String.format("%s ~ %s", startTime.format(TIME_FORMATTER), endTime.format(TIME_FORMATTER));
    }
}

