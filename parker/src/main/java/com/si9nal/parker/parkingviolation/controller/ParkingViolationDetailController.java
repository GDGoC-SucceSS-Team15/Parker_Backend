package com.si9nal.parker.parkingviolation.controller;

import com.si9nal.parker.parkingviolation.dto.res.ParkingViolationDetailResponseDto;
import com.si9nal.parker.parkingviolation.service.ParkingViolationDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import java.util.List;

@RestController
@RequestMapping("/api/parking-violation")
@Tag(name = "불법주정차 단속 구역 API", description = "주변 불법주정차 단속 구역 정보를 제공하는 API")
public class ParkingViolationDetailController {

    private final ParkingViolationDetailService parkingViolationDetailService;

    public ParkingViolationDetailController(ParkingViolationDetailService parkingViolationDetailService) {
        this.parkingViolationDetailService = parkingViolationDetailService;
    }

    @GetMapping
    @Operation(
            summary = "불법 주정차 단속 위치 정보 전체 조회",
            description = "불법 주정차 단속 위치 정보를 모두 조회합니다."
    )
    public ResponseEntity<ApiResponse<List<ParkingViolationDetailResponseDto>>> getAllParkingViolations() {
        List<ParkingViolationDetailResponseDto> allParkingViolations = parkingViolationDetailService.getAllParkingViolationDetails();
        return ResponseEntity.ok(ApiResponse.onSuccess(allParkingViolations));
    }

    @GetMapping("/detailed-location/{detailedLocation}")
    @Operation(
            summary = "불법 주정차 단속 위치 정보 상세위치별 상세 조회",
            description = "불법 주정차 단속 위치의 detailedLocation을 기준으로 상세 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<ParkingViolationDetailResponseDto>> getParkingViolationDetail(@PathVariable String detailedLocation) {
        return ResponseEntity.ok(ApiResponse.onSuccess(parkingViolationDetailService.getParkingViolationDetailByDetailedLocation(detailedLocation)));
    }

    @GetMapping("/id/{id}")
    @Operation(
            summary = "불법 주정차 단속 위치 정보 ID별 상세 조회",
            description = "불법 주정차 단속 위치의 ID를 기준으로 상세 정보를 조회합니다."
    )
    public ResponseEntity<ApiResponse<ParkingViolationDetailResponseDto>> getParkingViolationDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.onSuccess(parkingViolationDetailService.getParkingViolationDetailById(id)));
    }
}
