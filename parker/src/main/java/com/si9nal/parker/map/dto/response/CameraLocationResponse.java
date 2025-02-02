package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.camera.domain.CameraLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CameraLocationResponse {
    private String address;
    private Double latitude;
    private Double longitude;
    private String areaName;
    private String classification;

    public static CameraLocationResponse fromEntity(CameraLocation camera) {
        return new CameraLocationResponse(
                camera.getAddress(),
                camera.getLatitude(),
                camera.getLongitude(),
                camera.getAreaName(),
                camera.getClassification()
        );
    }
}
