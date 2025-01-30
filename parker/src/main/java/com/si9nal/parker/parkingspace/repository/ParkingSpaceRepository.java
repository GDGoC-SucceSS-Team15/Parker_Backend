package com.si9nal.parker.parkingspace.repository;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSpaceRepository extends CrudRepository<ParkingSpace, Long> {
    @Query(value = "SELECT * FROM parking_space " +
            "WHERE MBRContains(ST_LineStringFromText(?1, 4326), point)",
            nativeQuery = true)
    List<ParkingSpace> findWithinRectangle(String lineStringWkt);
}