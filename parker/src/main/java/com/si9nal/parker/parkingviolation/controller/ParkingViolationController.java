package com.si9nal.parker.parkingviolation.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.global.common.apiPayload.code.status.SuccessStatus;
import com.si9nal.parker.parkingviolation.dto.res.ParkingViolationResDto;
import com.si9nal.parker.parkingviolation.service.ParkingViolationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;;

@Tag(name = "주정차 금지구역 리스트 API", description = "주정차금지구역 리스트 조회를 제공하는 API")
@RestController
@RequestMapping("/api/parking-violation")
public class ParkingViolationController {

    private final ParkingViolationService parkingViolationService;

    public ParkingViolationController(ParkingViolationService parkingViolationService) {
        this.parkingViolationService = parkingViolationService;
    }

    @Operation(summary = "시도 & 시군구 이름으로 주정차 금지구역 리스트 조회", description = "시도 & 시군구 이름을 파라미터로 받아 주정차 금지구역 리스트를 조회합니다.")
    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<ParkingViolationResDto>>> getParkingViolations(
            @Parameter(description = "시도 이름", example = "서울특별시", required = true)
            @RequestParam String sidoName,
            @Parameter(description = "시군구 이름", example = "중랑구", required = true)
            @RequestParam String sigunguName){

        List<ParkingViolationResDto> response = parkingViolationService.getParkingViolations(sidoName, sigunguName);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, response));
    }
}
