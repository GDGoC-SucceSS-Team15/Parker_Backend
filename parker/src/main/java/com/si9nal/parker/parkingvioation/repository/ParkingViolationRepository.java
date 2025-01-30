package com.si9nal.parker.parkingvioation.repository;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingViolationRepository extends JpaRepository<ParkingViolation, Long> {
}
