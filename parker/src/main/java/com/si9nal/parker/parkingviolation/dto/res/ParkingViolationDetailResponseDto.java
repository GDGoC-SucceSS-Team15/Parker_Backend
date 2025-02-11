package com.si9nal.parker.parkingviolation.dto.res;

import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class ParkingViolationDetailResponseDto {
    private Long id;
    private String sidoName;
    private String sigunguName;
    private String roadName;
    private String detailedLocation;
    private String managementPhoneNumber;
    private String weekdayTime;
    private String saturdayTime;
    private String holidayTime;

    public static ParkingViolationDetailResponseDto fromEntity(ParkingViolation violation) {
        return ParkingViolationDetailResponseDto.builder()
                .id(violation.getId())
                .sidoName(violation.getSidoName())
                .sigunguName(violation.getSigunguName())
                .roadName(violation.getRoadName())
                .detailedLocation(violation.getDetailedLocation())
                .managementPhoneNumber(violation.getManagementPhoneNumber())
                .weekdayTime(formatTimeRange(violation.getWeekdayStartTime(), violation.getWeekdayEndTime()))
                .saturdayTime(formatTimeRange(violation.getSaturdayStartTime(), violation.getSaturdayEndTime()))
                .holidayTime(formatTimeRange(violation.getHolidayStartTime(), violation.getHolidayEndTime()))
                .build();
    }

    private static String formatTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return "단속 없음";
        }
        return startTime.toString() + " ~ " + endTime.toString();
    }
}
