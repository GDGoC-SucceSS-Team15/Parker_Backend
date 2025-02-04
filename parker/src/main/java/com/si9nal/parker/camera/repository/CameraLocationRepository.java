package com.si9nal.parker.camera.repository;

import com.si9nal.parker.camera.domain.CameraLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraLocationRepository extends JpaRepository<CameraLocation, Long> {

    @Query("SELECT c FROM CameraLocation c " +
            "WHERE c.latitude BETWEEN :minLat AND :maxLat " +
            "AND c.longitude BETWEEN :minLon AND :maxLon")
    List<CameraLocation> findCameraLocationsWithinBounds(
            @Param("minLat") Double minLat, @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon, @Param("maxLon") Double maxLon);
}