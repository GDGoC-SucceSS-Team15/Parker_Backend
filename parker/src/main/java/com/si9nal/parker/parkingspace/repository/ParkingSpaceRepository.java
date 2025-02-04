package com.si9nal.parker.parkingspace.repository;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    @Query("SELECT p FROM ParkingSpace p " +
            "WHERE p.latitude BETWEEN :minLat AND :maxLat " +
            "AND p.longitude BETWEEN :minLon AND :maxLon")
    List<ParkingSpace> findParkingSpacesWithinBounds(
            @Param("minLat") Double minLat, @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon, @Param("maxLon") Double maxLon);

    @Query(value = "SELECT * FROM parking_space " +
            "WHERE MBRContains(ST_LineStringFromText(?1, 4326), point)",
            nativeQuery = true)
    List<ParkingSpace> findWithinRectangle(String lineStringWkt);
}

