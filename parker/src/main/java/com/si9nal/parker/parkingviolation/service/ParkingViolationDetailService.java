package com.si9nal.parker.parkingviolation.service;

import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import com.si9nal.parker.parkingviolation.dto.res.ParkingViolationDetailResponseDto;
import com.si9nal.parker.parkingviolation.repository.ParkingViolationRepository;
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
        return parkingViolationRepository.findAll().stream()
                .map(ParkingViolationDetailResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ParkingViolationDetailResponseDto getParkingViolationDetailByDetailedLocation(String detailedLocation) {
        ParkingViolation violation = parkingViolationRepository.findByDetailedLocation(detailedLocation)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PARKING_SPACE_NOT_FOUND));

        return ParkingViolationDetailResponseDto.fromEntity(violation);
    }

    public ParkingViolationDetailResponseDto getParkingViolationDetailById(Long id) {
        ParkingViolation violation = parkingViolationRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PARKING_SPACE_NOT_FOUND));

        return ParkingViolationDetailResponseDto.fromEntity(violation);
    }
}
