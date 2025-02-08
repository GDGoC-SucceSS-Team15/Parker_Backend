package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import lombok.Builder;
import lombok.Getter;

/**
 * 메인 지도 화면에서 마커로 표시하는 주정차 금지구역 정보 DTO
 */
@Getter
@Builder
public class ParkingViolationSimpleResponse {
    private Long id;
    private String sidoName;
    private String sigunguName;
    private String roadName;
    private String detailedLocation;

    public static ParkingViolationSimpleResponse fromEntity(ParkingViolation parkingViolation) {
        return ParkingViolationSimpleResponse.builder()
                .id(parkingViolation.getId())
                .sidoName(parkingViolation.getSidoName())
                .sigunguName(parkingViolation.getSigunguName())
                .roadName(parkingViolation.getRoadName())
                .detailedLocation(parkingViolation.getDetailedLocation())
                .build();
    }
}
