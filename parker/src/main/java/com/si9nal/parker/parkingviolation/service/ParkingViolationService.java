package com.si9nal.parker.parkingviolation.service;

import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import com.si9nal.parker.parkingviolation.dto.res.ParkingViolationResDto;
import com.si9nal.parker.parkingviolation.repository.ParkingViolationRepository;
import com.si9nal.parker.parkingviolation.util.SidoNameUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class ParkingViolationService {

    private final ParkingViolationRepository parkingViolationRepository;

    public ParkingViolationService(ParkingViolationRepository parkingViolationRepository) {
        this.parkingViolationRepository = parkingViolationRepository;
    }

    /**
     * 시도 & 시군구 이름을 받아 주정차 금지구역 리스트를 조회
     */
    public List<ParkingViolationResDto> getParkingViolations(String sidoName, String sigunguName) {

        // 시도 이름 표준화
        String standardizedSidoName = SidoNameUtil.standardizeSidoName(sidoName);
        List<String> sidoNames = SidoNameUtil.getPossibleSidoNames(standardizedSidoName);

        List<ParkingViolation> parkingViolations = parkingViolationRepository.findBySidoNamesAndSigunguName(sidoNames, sigunguName);

        return parkingViolations.stream()
                .map(ParkingViolationResDto::fromEntity)
                .collect(Collectors.toList());
    }
}
