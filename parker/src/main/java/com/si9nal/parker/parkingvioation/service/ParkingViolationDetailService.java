package com.si9nal.parker.parkingvioation.service;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import com.si9nal.parker.parkingvioation.dto.res.ParkingViolationDetailResponseDto;
import com.si9nal.parker.parkingvioation.repository.ParkingViolationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingViolationDetailService {

    private final ParkingViolationRepository parkingViolationRepository;

    public ParkingViolationDetailService(ParkingViolationRepository parkingViolationRepository) {
        this.parkingViolationRepository = parkingViolationRepository;
    }

    public List<ParkingViolationDetailResponseDto> getAllParkingViolationDetails() {
        List<ParkingViolation> parkingViolations = parkingViolationRepository.findAll();

        return parkingViolations.stream()
                .map(ParkingViolationDetailResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ParkingViolationDetailResponseDto getParkingViolationDetailByDetailedLocation(String detailedLocation) {
        ParkingViolation violation = parkingViolationRepository.findByDetailedLocation(detailedLocation)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치의 불법 주정차 정보가 없습니다."));

        return ParkingViolationDetailResponseDto.fromEntity(violation);
    }

    public ParkingViolationDetailResponseDto getParkingViolationDetailById(Long id) {
        ParkingViolation violation = parkingViolationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치의 불법 주정차 정보가 없습니ㅏㄷ."));

        return ParkingViolationDetailResponseDto.fromEntity(violation);
    }
}
