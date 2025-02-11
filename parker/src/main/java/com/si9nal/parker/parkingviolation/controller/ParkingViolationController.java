package com.si9nal.parker.parkingviolation.controller;

import com.si9nal.parker.parkingviolation.dto.res.ParkingViolationResDto;
import com.si9nal.parker.parkingviolation.service.ParkingViolationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
    @RequestMapping("/api/parking-violation")
    public class ParkingViolationController {
        private final ParkingViolationService parkingViolationService;

    public ParkingViolationController(ParkingViolationService parkingViolationService) {
        this.parkingViolationService = parkingViolationService;
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ParkingViolationResDto>> getParkingViolations(@RequestParam String sigunguName) {
        List<ParkingViolationResDto> response = parkingViolationService.getBySigunguName(sigunguName);
        return ResponseEntity.ok(response);
    }
}
