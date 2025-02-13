package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.global.common.util.DtoFormatUtil;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;

import lombok.Builder;
import lombok.Getter;

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
                .parkingName(DtoFormatUtil.getValueOrDefault(parkingSpace.getParkingName()))
                .address(DtoFormatUtil.getValueOrDefault(parkingSpace.getAddress()))

                .weekdayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))

                .baseParkingFee((DtoFormatUtil.getValueOrDefault(parkingSpace.getBaseParkingFee())))
                .baseParkingTime(DtoFormatUtil.getValueOrDefault(parkingSpace.getBaseParkingTime()))
                .distance(DtoFormatUtil.formatDistance(distance))
                .build();
    }
}