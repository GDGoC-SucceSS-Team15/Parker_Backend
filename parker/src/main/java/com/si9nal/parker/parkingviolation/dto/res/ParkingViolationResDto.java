package com.si9nal.parker.parkingviolation.dto.res;

import com.si9nal.parker.global.common.util.DtoFormatUtil;
import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ParkingViolationResDto {
    private Long id;
    private String sidoName;
    private String sigunguName;
    private String roadName;
    private String detailedLocation;
    private String managementPhoneNumber;

    private String weekdayTime;
    private String saturdayTime;
    private String holidayTime;

    public static ParkingViolationResDto fromEntity(ParkingViolation parkingViolation) {
        return ParkingViolationResDto.builder()
                .id(parkingViolation.getId())
                .sidoName(parkingViolation.getSidoName())
                .sigunguName(parkingViolation.getSigunguName())
                .roadName(parkingViolation.getRoadName())
                .detailedLocation(parkingViolation.getDetailedLocation())
                .managementPhoneNumber(parkingViolation.getManagementPhoneNumber())

                .weekdayTime(DtoFormatUtil.formatTimeRange(parkingViolation.getWeekdayStartTime(), parkingViolation.getWeekdayEndTime()))
                .saturdayTime(DtoFormatUtil.formatTimeRange(parkingViolation.getSaturdayStartTime(), parkingViolation.getSaturdayEndTime()))
                .holidayTime(DtoFormatUtil.formatTimeRange(parkingViolation.getHolidayStartTime(), parkingViolation.getHolidayEndTime()))
                .build();
    }
}
