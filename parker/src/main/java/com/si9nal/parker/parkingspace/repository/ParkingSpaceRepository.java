package com.si9nal.parker.parkingspace.repository;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingSpaceRepository extends CrudRepository<ParkingSpace, Long> {

    @Query(value = "SELECT * FROM parking_space " +
            "WHERE ST_Contains(ST_Buffer(ST_GeomFromText(?1), ?2), point)",
            nativeQuery = true)
    List<ParkingSpace> findWithinRadius(String pointWkt, Double radius);
}
