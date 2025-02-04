package com.si9nal.parker.map.service;

import com.si9nal.parker.camera.repository.CameraLocationRepository;
import com.si9nal.parker.map.dto.response.CameraLocationResponse;
import com.si9nal.parker.map.dto.response.Location;
import com.si9nal.parker.map.dto.response.ParkingSpaceResponse;
import com.si9nal.parker.map.dto.response.ParkingViolationResponse;
import com.si9nal.parker.map.util.GeometryUtil;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import com.si9nal.parker.parkingvioation.repository.ParkingViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapMainService {

    private final ParkingSpaceRepository parkingSpaceRepository;
    private final CameraLocationRepository cameraLocationRepository;
    private final ParkingViolationRepository parkingViolationRepository;

    /**
     * 사용자 위치 2km 이내의 주차공간과 2km 이내의 카메라 단속 위치
     */
    public Map<String, Object> findNearbyParkingSpacesAndCameras(Double latitude, Double longitude) {
        Location northEast = GeometryUtil.calculate(latitude, longitude, 2.0, 45.0);
        Location southWest = GeometryUtil.calculate(latitude, longitude, 2.0, 225.0);

        Location camera_northEast = GeometryUtil.calculate(latitude, longitude, 0.5, 45.0);
        Location camera_southWest = GeometryUtil.calculate(latitude, longitude, 0.5, 225.0);

        // 주차 공간 조회
        List<ParkingSpaceResponse> parkingSpaces = parkingSpaceRepository.findParkingSpacesWithinBounds(
                        southWest.getLatitude(), northEast.getLatitude(),
                        southWest.getLongitude(), northEast.getLongitude())
                .stream()
                .map(ParkingSpaceResponse::fromEntity)
                .collect(Collectors.toList());

        // 단속 카메라 위치 조회
        List<CameraLocationResponse> cameraLocations = cameraLocationRepository.findCameraLocationsWithinBounds(
                        camera_southWest.getLatitude(), camera_northEast.getLatitude(),
                        camera_southWest.getLongitude(), camera_northEast.getLongitude())
                .stream()
                .map(CameraLocationResponse::fromEntity)
                .collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        response.put("parkingSpaces", parkingSpaces);
        response.put("cameraLocations", cameraLocations);

        return response;
    }

    /**
     * 사용자 위치 2km 이내의 주차공간과 시군구 이름으로 주정차금지구역 불러오기
     */
    public Map<String, Object> findNearbyParkingSpacesAndNoStoppingZones(Double latitude, Double longitude, String sigunguName) {
        Location northEast = GeometryUtil.calculate(latitude, longitude, 2.0, 45.0);
        Location southWest = GeometryUtil.calculate(latitude, longitude, 2.0, 225.0);

        // 주차 공간 조회
        List<ParkingSpaceResponse> parkingSpaces = parkingSpaceRepository.findParkingSpacesWithinBounds(
                        southWest.getLatitude(), northEast.getLatitude(),
                        southWest.getLongitude(), northEast.getLongitude())
                .stream()
                .map(ParkingSpaceResponse::fromEntity)
                .collect(Collectors.toList());

        // 시군구이름으로 조회
        List<ParkingViolationResponse> parkingViolations = parkingViolationRepository.findBySigunguName(sigunguName)
                .stream()
                .map(ParkingViolationResponse::fromEntity)
                .collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        response.put("parkingSpaces", parkingSpaces);
        response.put("parkingViolations", parkingViolations);

        return response;
    }
}
