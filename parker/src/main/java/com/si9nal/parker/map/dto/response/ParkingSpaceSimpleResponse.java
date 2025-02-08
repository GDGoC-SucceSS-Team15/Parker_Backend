package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.Builder;
import lombok.Getter;

/**
 * 메인 지도 화면에서 마커로 표시하는 주차장 정보 DTO
 */
@Getter
@Builder
public class ParkingSpaceSimpleResponse {
    private Long id;
    private String parkingName;
    private Double latitude;
    private Double longitude;

    public static ParkingSpaceSimpleResponse fromEntity(ParkingSpace parkingSpace) {
        return ParkingSpaceSimpleResponse.builder()
                .id(parkingSpace.getId())
                .parkingName(parkingSpace.getParkingName())
                .latitude(parkingSpace.getLatitude())
                .longitude(parkingSpace.getLongitude())
                .build();
    }
}