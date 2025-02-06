package com.si9nal.parker.parkingspace.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.global.common.apiPayload.code.status.SuccessStatus;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import com.si9nal.parker.parkingspace.service.ParkingSpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parkingSpace")
@Tag(name = "주차 공간 API", description = "주변 주차 공간 정보를 제공하는 API")
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceController.class);
    private final ParkingSpaceService service;

    public ParkingSpaceController(ParkingSpaceService service) {
        this.service = service;
    }

    @GetMapping("/map")
    @Operation(
            summary = "주변 주차 공간 조회",
            description = "사용자의 현재 위치 기반으로 반경 2km 이내의 주차 공간을 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "주차 공간 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpaceMapDto.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 (위도/경도 값 오류)"
            )
    })
    public ResponseEntity<ApiResponse<List<ParkingSpaceMapDto>>> getNearbyParkingSpaces(
            @Parameter(description = "현재 위치의 위도", example = "37.5665", required = true)
            @RequestParam Double latitude,

            @Parameter(description = "현재 위치의 경도", example = "126.9780", required = true)
            @RequestParam Double longitude) {

        List<ParkingSpace> parkingSpaces = service.getParkingSpaces(latitude, longitude, 2.0); // 2km 반경

        // 주차 공간 DTO로 변환
        List<ParkingSpaceMapDto> parkingSpaceDtos = parkingSpaces.stream()
                .map(ParkingSpaceMapDto::fromEntity)
                .toList();

        // 성공 응답 반환 (결과가 비어있어도 성공으로 처리)
        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, parkingSpaceDtos)
        );
    }
}