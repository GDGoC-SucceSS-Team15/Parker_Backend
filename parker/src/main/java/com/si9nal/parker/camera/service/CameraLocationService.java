package com.si9nal.parker.camera.service;

import com.si9nal.parker.camera.repository.CameraLocationRepository;
import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.global.common.util.Direction;
import com.si9nal.parker.global.common.util.GeometryUtil;
import com.si9nal.parker.global.common.util.Location;
import com.si9nal.parker.map.dto.response.CameraLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CameraLocationService {

    private final CameraLocationRepository cameraLocationRepository;

    public List<CameraLocationResponse> findNearbyCameras (Double latitude, Double longitude) {

        validateCoordinates(latitude, longitude);

        Location camera_northEast = GeometryUtil.calculate(latitude, longitude, 0.5, Direction.NORTHEAST.getBearing());
        Location camera_southWest = GeometryUtil.calculate(latitude, longitude, 0.5, Direction.SOUTHWEST.getBearing());

        List<CameraLocationResponse> cameraLocations = cameraLocationRepository.findCameraLocationsWithinBounds(
                        camera_southWest.getLatitude(), camera_northEast.getLatitude(),
                        camera_southWest.getLongitude(), camera_northEast.getLongitude())
                .stream()
                .map(CameraLocationResponse::fromEntity)
                .collect(Collectors.toList());

        if(cameraLocations.isEmpty()) {
            throw new GeneralException(ErrorStatus.CAMERA_LOCATION_NOT_FOUND);
        }

        return cameraLocations;
    }

    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude == null || latitude < -90 || latitude > 90) {
            throw new GeneralException(ErrorStatus.CAMERA_LOCATION_INVALID_LATITUDE);
        }
        if (longitude == null || longitude < -180 || longitude > 180) {
            throw new GeneralException(ErrorStatus.CAMERA_LOCATION_INVALID_LONGITUDE);
        }
    }

}
