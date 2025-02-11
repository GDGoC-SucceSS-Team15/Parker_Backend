package com.si9nal.parker.parkingviolation.dto.res;

import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class ParkingViolationResDto {
    private Long id;
    private String sidoName;
    private String sigunguName;
    private String roadName;
    private String detailedLocation;
    private String managementPhoneNumber;
    private LocalTime weekdayStartTime;
    private LocalTime weekdayEndTime;
    private LocalTime saturdayStartTime;
    private LocalTime saturdayEndTime;
    private LocalTime holidayStartTime;
    private LocalTime holidayEndTime;

    public static ParkingViolationResDto fromEntity(ParkingViolation parkingViolation) {
        return ParkingViolationResDto.builder()
                .id(parkingViolation.getId())
                .sidoName(parkingViolation.getSidoName())
                .sigunguName(parkingViolation.getSigunguName())
                .roadName(parkingViolation.getRoadName())
                .detailedLocation(parkingViolation.getDetailedLocation())
                .managementPhoneNumber(parkingViolation.getManagementPhoneNumber())
                .weekdayStartTime(parkingViolation.getWeekdayStartTime())
                .weekdayEndTime(parkingViolation.getWeekdayEndTime())
                .saturdayStartTime(parkingViolation.getSaturdayStartTime())
                .saturdayEndTime(parkingViolation.getSaturdayEndTime())
                .holidayStartTime(parkingViolation.getHolidayStartTime())
                .holidayEndTime(parkingViolation.getHolidayEndTime())
                .build();
    }
}
