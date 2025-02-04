package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingSpaceResponse {
    private String parkingName;
    private String address;
    private Integer totalParkingSpaces;
    private Double latitude;
    private Double longitude;

    public static ParkingSpaceResponse fromEntity(ParkingSpace parkingSpace) {
        return ParkingSpaceResponse.builder()
                .parkingName(parkingSpace.getParkingName())
                .address(parkingSpace.getAddress())
                .totalParkingSpaces(parkingSpace.getTotalParkingSpaces())
                .latitude(parkingSpace.getLatitude())
                .longitude(parkingSpace.getLongitude())
                .build();
    }
}