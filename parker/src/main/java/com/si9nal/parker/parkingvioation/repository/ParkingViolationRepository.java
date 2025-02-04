package com.si9nal.parker.parkingvioation.repository;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingViolationRepository extends JpaRepository<ParkingViolation, Long> {
    List<ParkingViolation> findBySigunguName(String findBySigunguName);
}