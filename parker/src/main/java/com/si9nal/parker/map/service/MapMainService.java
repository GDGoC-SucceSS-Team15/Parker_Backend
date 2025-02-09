package com.si9nal.parker.map.service;

import com.si9nal.parker.bookmark.repository.SpaceBookmarkRepository;
import com.si9nal.parker.camera.repository.CameraLocationRepository;
import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
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
import java.util.Comparator;
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

        validateCoordinates(latitude, longitude);

        Location northEast = GeometryUtil.calculate(latitude, longitude, 2.0, 45.0);
        Location southWest = GeometryUtil.calculate(latitude, longitude, 2.0, 225.0);

        Location camera_northEast = GeometryUtil.calculate(latitude, longitude, 0.5, 45.0);
        Location camera_southWest = GeometryUtil.calculate(latitude, longitude, 0.5, 225.0);

        // 주차 공간 조회
        List<ParkingSpaceSimpleResponse> parkingSpaces = parkingSpaceRepository.findParkingSpacesWithinBounds(
                        southWest.getLatitude(), northEast.getLatitude(),
                        southWest.getLongitude(), northEast.getLongitude())
                .stream()
                .map(ParkingSpaceSimpleResponse::fromEntity)
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

        validateCoordinates(latitude, longitude);

        Location northEast = GeometryUtil.calculate(latitude, longitude, 2.0, 45.0);
        Location southWest = GeometryUtil.calculate(latitude, longitude, 2.0, 225.0);

        // 주차 공간 조회
        List<ParkingSpaceSimpleResponse> parkingSpaces = parkingSpaceRepository.findParkingSpacesWithinBounds(
                        southWest.getLatitude(), northEast.getLatitude(),
                        southWest.getLongitude(), northEast.getLongitude())
                .stream()
                .map(ParkingSpaceSimpleResponse::fromEntity)
                .collect(Collectors.toList());

        // 시군구이름으로 조회
        List<ParkingViolationSimpleResponse> parkingViolations = parkingViolationRepository.findBySigunguName(sigunguName)
                .stream()
                .map(ParkingViolationSimpleResponse::fromEntity)
                .collect(Collectors.toList());


        Map<String, Object> response = new HashMap<>();
        response.put("parkingSpaces", parkingSpaces);
        response.put("parkingViolations", parkingViolations);

        return response;
    }


    /**
     * 메인 지도에서 주차장의 기본적인 상세정보를 조회
     */
    public ParkingSpaceSummaryResponse getParkingSpaceDetail(Long id, Principal principal, Double latitude, Double longitude) {

        validateCoordinates(latitude, longitude);

        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PARKING_SPACE_NOT_FOUND));

        boolean isBookmarked = false;

        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
            isBookmarked = spaceBookmarkRepository.existsByUserAndParkingSpace(user, parkingSpace);
        }

        double lat = latitude;
        double lon = longitude;

        double distance = GeometryUtil.calculateDistance(lat, lon, parkingSpace.getLatitude(), parkingSpace.getLongitude());

        return ParkingSpaceSummaryResponse.of(parkingSpace, isBookmarked, distance);
    }

    /**
     * 현재 위치 근처 주차장 리스트에서 하나의 주차장의 상세정보 조회
     */
    public ParkingSpaceDetailResponse getParkingSpaceNearbyDetail(Long id) {
        ParkingSpace parkingSpace = parkingSpaceRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PARKING_SPACE_NOT_FOUND));

        return ParkingSpaceDetailResponse.fromEntity(parkingSpace);
    }


    /**
     * 2km 이내의 현재 위치 근처 주차장 리스트 조회
     */
    public ParkingSpaceNearbyResponseList findNearbyParkingSpaces(Double latitude, Double longitude) {

        validateCoordinates(latitude, longitude);

        Location northEast = GeometryUtil.calculate(latitude, longitude, 2.0, 45.0);
        Location southWest = GeometryUtil.calculate(latitude, longitude, 2.0, 225.0);

        // 2km 범위 내 주차장 조회
        List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findParkingSpacesWithinBounds(
                southWest.getLatitude(), northEast.getLatitude(),
                southWest.getLongitude(), northEast.getLongitude());

        // 거리 계산 후 정렬
        List<ParkingSpaceNearbyResponse> responses = parkingSpaces.stream()
                .map(space -> {
                    double distance = GeometryUtil.calculateDistance(latitude, longitude, space.getLatitude(), space.getLongitude());
                    return new ParkingSpaceWithDistance(space, distance);
                })
                .sorted(Comparator.comparingDouble(ParkingSpaceWithDistance::getDistance))
                .map(ParkingSpaceWithDistance::toResponse)
                .collect(Collectors.toList());

        return ParkingSpaceNearbyResponseList.from(responses);
    }

    // 주차장 엔티티와 거리 정보를 함께 담을 클래스
    private static class ParkingSpaceWithDistance {
        private final ParkingSpace parkingSpace;
        private final double distance;

        public ParkingSpaceWithDistance(ParkingSpace parkingSpace, double distance) {
            this.parkingSpace = parkingSpace;
            this.distance = distance;
        }

        public double getDistance() {
            return distance;
        }

        public ParkingSpaceNearbyResponse toResponse() {
            return ParkingSpaceNearbyResponse.of(parkingSpace, distance);
        }
    }

    /**
     * 위도와 경도의 범위 유효성을 검사하는 메서드
     */
    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude == null || latitude < -90 || latitude > 90) {
            throw new GeneralException(ErrorStatus.PARKING_SPACE_INVALID_LATITUDE);
        }
        if (longitude == null || longitude < -180 || longitude > 180) {
            throw new GeneralException(ErrorStatus.PARKING_SPACE_INVALID_LONGITUDE);
        }
    }
}
