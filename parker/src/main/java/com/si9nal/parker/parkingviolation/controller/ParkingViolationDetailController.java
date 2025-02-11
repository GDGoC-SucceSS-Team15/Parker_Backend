package com.si9nal.parker.parkingviolation.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.parkingviolation.dto.res.ParkingViolationDetailResponseDto;
import com.si9nal.parker.parkingviolation.service.ParkingViolationDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parking-violation")
public class ParkingViolationDetailController {

    private final ParkingViolationDetailService parkingViolationDetailService;

    public ParkingViolationDetailController(ParkingViolationDetailService parkingViolationDetailService) {
        this.parkingViolationDetailService = parkingViolationDetailService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ParkingViolationDetailResponseDto>>> getAllParkingViolations() {
        List<ParkingViolationDetailResponseDto> allParkingViolations = parkingViolationDetailService.getAllParkingViolationDetails();
        return ResponseEntity.ok(ApiResponse.onSuccess(allParkingViolations));
    }

    @GetMapping("/detailedLocation/{detailedLocation}")
    public ResponseEntity<ApiResponse<ParkingViolationDetailResponseDto>> getParkingViolationDetail(@PathVariable String detailedLocation) {
        return ResponseEntity.ok(ApiResponse.onSuccess(parkingViolationDetailService.getParkingViolationDetailByDetailedLocation(detailedLocation)));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<ParkingViolationDetailResponseDto>> getParkingViolationDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.onSuccess(parkingViolationDetailService.getParkingViolationDetailById(id)));
    }
}
