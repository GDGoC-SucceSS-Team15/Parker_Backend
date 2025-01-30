package com.si9nal.parker.parkingspace.service;

import com.si9nal.parker.global.common.util.Direction;
import com.si9nal.parker.global.common.util.GeometryUtil;
import com.si9nal.parker.global.common.util.Location;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
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

    public List<ParkingSpace> getParkingSpaces(Double latitude, Double longitude, Double distance) throws Exception {
        // 북동쪽과 남서쪽 좌표 계산
        Location northEast = GeometryUtil.calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        // LINESTRING 형식의 WKT 문자열 생성
        String lineStringWkt = String.format("LINESTRING(%f %f, %f %f)",
                northEast.getLatitude(), northEast.getLongitude(),
                southWest.getLatitude(), southWest.getLongitude());

        return repository.findWithinRectangle(lineStringWkt);
    }
}
