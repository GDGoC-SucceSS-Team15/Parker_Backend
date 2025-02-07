package com.si9nal.parker.map.service;

import com.si9nal.parker.bookmark.repository.SpaceBookmarkRepository;
import com.si9nal.parker.camera.repository.CameraLocationRepository;
import com.si9nal.parker.map.dto.response.*;
import com.si9nal.parker.map.util.GeometryUtil;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import com.si9nal.parker.parkingvioation.repository.ParkingViolationRepository;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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

    private final UserRepository userRepository;
    private final SpaceBookmarkRepository spaceBookmarkRepository;

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


    /**
     * 메인 지도에서 주차장의 기본적인 상세정보를 조회
     */
    public ParkingSpaceSummaryResponse getParkingSpaceDetail(Long id, Principal principal) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주차장을 찾을 수 없습니다."));

        boolean isBookmarked = false;

        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            isBookmarked = spaceBookmarkRepository.existsByUserAndParkingSpace(user, parkingSpace);
        }

        return ParkingSpaceSummaryResponse.of(parkingSpace, isBookmarked);
    }
}
