package com.si9nal.parker.parkingvioation.service;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import com.si9nal.parker.parkingvioation.dto.res.ParkingViolationResDto;
import com.si9nal.parker.parkingvioation.repository.ParkingViolationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingViolationService {
    private final ParkingViolationRepository parkingViolationRepository;

    public ParkingViolationService(ParkingViolationRepository parkingViolationRepository) {
        this.parkingViolationRepository = parkingViolationRepository;
    }

    public List<ParkingViolationResDto> getBySigunguName(String sigunguName) {
        List<ParkingViolation> parkingViolations = parkingViolationRepository.findBySigunguName(sigunguName);

        return parkingViolations.stream()
                .map(ParkingViolationResDto::fromEntity)
                .collect(Collectors.toList());
    }
}
