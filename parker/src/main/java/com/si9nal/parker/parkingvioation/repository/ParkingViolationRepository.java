package com.si9nal.parker.parkingvioation.repository;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingViolationRepository extends JpaRepository<ParkingViolation, Long> {
    Optional<ParkingViolation> findByDetailedLocation(String detailedLocation);
}
