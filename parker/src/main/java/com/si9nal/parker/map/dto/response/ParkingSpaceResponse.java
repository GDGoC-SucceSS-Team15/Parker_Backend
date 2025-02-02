package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParkingSpaceResponse {
    private String parkingName;
    private String address;
    private Integer totalParkingSpaces;
    private Double latitude;
    private Double longitude;

    public static ParkingSpaceResponse fromEntity(ParkingSpace parkingSpace) {
        return new ParkingSpaceResponse(
                parkingSpace.getParkingName(),
                parkingSpace.getAddress(),
                parkingSpace.getTotalParkingSpaces(),
                parkingSpace.getLatitude(),
                parkingSpace.getLongitude()
        );
    }
}