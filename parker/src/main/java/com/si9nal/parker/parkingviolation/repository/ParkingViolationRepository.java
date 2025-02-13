package com.si9nal.parker.parkingviolation.repository;

import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface ParkingViolationRepository extends JpaRepository<ParkingViolation, Long> {
    //List<ParkingViolation> findBySigunguName(String findBySigunguName);

    Optional<ParkingViolation> findByDetailedLocation(String detailedLocation);

    //List<ParkingViolation> findBySidoNameAndSigunguNameContaining(String sidoName, String sigunguName);

    @Query("SELECT p FROM ParkingViolation p WHERE p.sidoName IN :sidoNames AND p.sigunguName LIKE %:sigunguName%")
    List<ParkingViolation> findBySidoNamesAndSigunguName(@Param("sidoNames") List<String> sidoNames, @Param("sigunguName") String sigunguName);
}
