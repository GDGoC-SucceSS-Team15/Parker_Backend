package com.si9nal.parker.parkingvioation.controller;

import com.si9nal.parker.parkingvioation.dto.res.ParkingViolationDetailResponseDto;
import com.si9nal.parker.parkingvioation.service.ParkingViolationDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/parking-violation")
public class ParkingViolationDetailController {

    private final ParkingViolationDetailService parkingViolationDetailService;

    public ParkingViolationDetailController(ParkingViolationDetailService parkingViolationDetailService) {
        this.parkingViolationDetailService = parkingViolationDetailService;
    }

    @GetMapping
    public ResponseEntity<List<ParkingViolationDetailResponseDto>> getAllParkingViolations() {
        List<ParkingViolationDetailResponseDto> allParkingViolations = parkingViolationDetailService.getAllParkingViolationDetails();

        if (allParkingViolations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allParkingViolations);
    }

    @GetMapping("/{detailedLocation}")
    public ResponseEntity<ParkingViolationDetailResponseDto> getParkingViolationDetail(@PathVariable String detailedLocation) {
        ParkingViolationDetailResponseDto reponse= parkingViolationDetailService.getParkingViolationDetailByDetailedLocation(detailedLocation);
        return ResponseEntity.ok(reponse);
    }
}
