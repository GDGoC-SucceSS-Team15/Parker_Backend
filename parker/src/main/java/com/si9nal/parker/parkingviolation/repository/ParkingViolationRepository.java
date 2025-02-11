package com.si9nal.parker.parkingviolation.repository;

import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ParkingViolationRepository extends JpaRepository<ParkingViolation, Long> {
    List<ParkingViolation> findBySigunguName(String findBySigunguName);

    Optional<ParkingViolation> findByDetailedLocation(String detailedLocation);
}
