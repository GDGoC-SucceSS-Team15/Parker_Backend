package com.si9nal.parker.parkingspace.dto.res;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.AccessLevel;
import org.locationtech.jts.geom.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapDto {
    private final Long id;
    private final String parkingName;
    private final String address;
    private final Double latitude;
    private final Double longitude;
    private final LocalTime weekdayStartTime;
    private final LocalTime weekdayEndTime;
    private final LocalTime saturdayStartTime;
    private final LocalTime saturdayEndTime;
    private final LocalTime holidayStartTime;
    private final LocalTime holidayEndTime;
    private final Integer baseParkingTime;
    private final Integer baseParkingFee;

    public static ParkingSpaceMapDto fromEntity(ParkingSpace entity) {
        return ParkingSpaceMapDto.builder()
                .id(entity.getId())
                .parkingName(entity.getParkingName())
                .address(entity.getAddress())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .weekdayStartTime(entity.getWeekdayStartTime())
                .weekdayEndTime(entity.getWeekdayEndTime())
                .saturdayStartTime(entity.getSaturdayStartTime())
                .saturdayEndTime(entity.getSaturdayEndTime())
                .holidayStartTime(entity.getHolidayStartTime())
                .holidayEndTime(entity.getHolidayEndTime())
                .baseParkingTime(entity.getBaseParkingTime())
                .baseParkingFee(entity.getBaseParkingFee())
                .build();
    }
}
