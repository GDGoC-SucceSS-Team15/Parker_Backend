package com.si9nal.parker.parkingspace.dto.res;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import org.locationtech.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class ParkingSpaceMapDto {
    private Long id;
    private String parkingName;
    private String address;
    private Double latitude;
    private Double longitude;

    private Point point;

    private LocalTime weekdayStartTime;
    private LocalTime weekdayEndTime;
    private LocalTime saturdayStartTime;
    private LocalTime saturdayEndTime;
    private LocalTime holidayStartTime;
    private LocalTime holidayEndTime;

    private Integer baseParkingTime;
    private Integer baseParkingFee;

    public static ParkingSpaceMapDto fromEntity(ParkingSpace parkingSpace) {
        return ParkingSpaceMapDto.builder()
                .id(parkingSpace.getId())
                .parkingName(parkingSpace.getParkingName())
                .address(parkingSpace.getAddress())
                .latitude(parkingSpace.getLatitude())
                .longitude(parkingSpace.getLongitude())
                .point(parkingSpace.getPoint())
                .weekdayStartTime(parkingSpace.getWeekdayStartTime())
                .weekdayEndTime(parkingSpace.getWeekdayEndTime())
                .saturdayStartTime(parkingSpace.getSaturdayStartTime())
                .saturdayEndTime(parkingSpace.getSaturdayEndTime())
                .holidayStartTime(parkingSpace.getHolidayStartTime())
                .holidayEndTime(parkingSpace.getHolidayEndTime())
                .baseParkingTime(parkingSpace.getBaseParkingTime())
                .baseParkingFee(parkingSpace.getBaseParkingFee())
                .build();
    }
}
