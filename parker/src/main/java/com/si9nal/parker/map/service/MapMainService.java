package com.si9nal.parker.map.service;

import com.si9nal.parker.camera.domain.CameraLocation;
import com.si9nal.parker.camera.repository.CameraLocationRepository;
import com.si9nal.parker.map.dto.response.CameraLocationResponse;
import com.si9nal.parker.map.dto.response.Location;
import com.si9nal.parker.map.dto.response.ParkingSpaceResponse;
import com.si9nal.parker.map.util.GeometryUtil;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
//import com.si9nal.parker.parkingvioation.repository.ParkingViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MapMainService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final CameraLocationRepository cameraLocationRepository;


    public Map<String, Object> findNearbyParkingSpacesAndCameras(Double latitude, Double longitude) {
        Location userLocation = new Location(latitude, longitude);
        Location northEast = GeometryUtil.calculate(latitude, longitude, 2.0, 45.0);
        Location southWest = GeometryUtil.calculate(latitude, longitude, 2.0, 225.0);

        // 주차 공간 조회
        List<ParkingSpaceResponse> parkingSpaces = parkingSpaceRepository.findParkingSpacesWithinBounds(
                        southWest.getLatitude(), northEast.getLatitude(),
                        southWest.getLongitude(), northEast.getLongitude())
                .stream()
                .map(ParkingSpaceResponse::fromEntity)
                .collect(Collectors.toList());

        // 카메라 위치 조회
        List<CameraLocationResponse> cameraLocations = cameraLocationRepository.findCameraLocationsWithinBounds(
                southWest.getLatitude(), northEast.getLatitude(),
                southWest.getLongitude(), northEast.getLongitude())
                .stream()
                .map(CameraLocationResponse::fromEntity)
                .collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        response.put("parkingSpaces", parkingSpaces);
        response.put("cameraLocations", cameraLocations);

        return response;
    }
}
