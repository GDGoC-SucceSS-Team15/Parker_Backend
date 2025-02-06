package com.si9nal.parker.parkingspace.service;

import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.global.common.util.Direction;
import com.si9nal.parker.global.common.util.GeometryUtil;
import com.si9nal.parker.global.common.util.Location;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ParkingSpaceService {
    private final ParkingSpaceRepository repository;

    public ParkingSpaceService(ParkingSpaceRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceService.class);

    public List<ParkingSpace> getParkingSpaces(Double latitude, Double longitude, Double distance) {
        logger.info("Fetching parking spaces for lat: {}, lon: {}, distance: {}", latitude, longitude, distance);

        // 위도, 경도 유효성 검사 추가
        validateCoordinates(latitude, longitude);

        // 북동쪽과 남서쪽 좌표 계산
        Location northEast = GeometryUtil.calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        // LINESTRING 형식의 WKT 문자열 생성
        String lineStringWkt = String.format("LINESTRING(%f %f, %f %f)",
                northEast.getLatitude(), northEast.getLongitude(),
                southWest.getLatitude(), southWest.getLongitude());

        List<ParkingSpace> parkingSpaces = repository.findWithinRectangle(lineStringWkt);

        if (parkingSpaces.isEmpty()) {
            throw new GeneralException(ErrorStatus.PARKING_SPACE_NOT_FOUND);
        }

        return parkingSpaces;
    }

    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new GeneralException(ErrorStatus.PARKING_SPACE_INVALID_LATITUDE);
        }
        if (longitude < -180 || longitude > 180) {
            throw new GeneralException(ErrorStatus.PARKING_SPACE_INVALID_LONGITUDE);
        }
    }
}
