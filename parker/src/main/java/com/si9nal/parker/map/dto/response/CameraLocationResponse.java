package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.camera.domain.CameraLocation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CameraLocationResponse {
    private String address;
    private Double latitude;
    private Double longitude;
    private String areaName;
    private String classification;

    public static CameraLocationResponse fromEntity(CameraLocation camera) {
        return CameraLocationResponse.builder()
                .address(camera.getAddress())
                .latitude(camera.getLatitude())
                .longitude(camera.getLongitude())
                .areaName(camera.getAreaName())
                .classification(camera.getClassification())
                .build();
    }
}
