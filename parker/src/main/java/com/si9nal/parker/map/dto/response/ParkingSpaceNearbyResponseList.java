package com.si9nal.parker.map.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ParkingSpaceNearbyResponseList {
    private List<ParkingSpaceNearbyResponse> parkingSpaceNearbyResponseList;

    public static ParkingSpaceNearbyResponseList from(List<ParkingSpaceNearbyResponse> parkingSpaceNearbyResponses) {
        return new ParkingSpaceNearbyResponseList(parkingSpaceNearbyResponses);
    }
}