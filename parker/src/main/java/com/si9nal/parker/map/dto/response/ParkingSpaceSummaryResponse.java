package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.global.common.util.DtoFormatUtil;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.Builder;
import lombok.Getter;

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

    private String distance;

    public static ParkingSpaceSummaryResponse of(ParkingSpace parkingSpace, boolean isBookmarked, Double distance) {
        return ParkingSpaceSummaryResponse.builder()
                .id(parkingSpace.getId())
                .parkingName(parkingSpace.getParkingName())
                .weekdayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))
                .baseParkingFee(DtoFormatUtil.getValueOrDefault(parkingSpace.getBaseParkingFee()))
                .latitude(parkingSpace.getLatitude())
                .longitude(parkingSpace.getLongitude())
                .isBookmarked(isBookmarked)
                .distance(DtoFormatUtil.formatDistance(distance))
                .build();
    }
}