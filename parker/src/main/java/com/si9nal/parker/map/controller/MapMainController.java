package com.si9nal.parker.map.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.global.common.apiPayload.code.status.SuccessStatus;

import com.si9nal.parker.map.dto.response.ParkingSpaceDetailResponse;
import com.si9nal.parker.map.dto.response.ParkingSpaceNearbyResponseList;
import com.si9nal.parker.map.dto.response.ParkingSpaceSummaryResponse;
import com.si9nal.parker.map.service.MapMainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Tag(name = "메인 지도 API", description = "메인 지도화면에서 주차장, 주정차금지구역 조회를 제공하는 API")
@RestController
@Slf4j
@RequestMapping("/api/parker")
@RequiredArgsConstructor
public class MapMainController {

    private final MapMainService mapMainService;

    @Operation(summary = "주차공간과 단속카메라 위치들을 조회", description = "메인 지도에서 사용자의 위치 2km 안의 주차공간과 단속카메라의 위치를 불러옵니다. (확정기능)")
    @GetMapping("/v1/map-main")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMapWithParkingSpacesAndCameraLocation(
            @Parameter(description = "사용자의 위도", example = "37.5665", required = true)
            @RequestParam Double latitude,
            @Parameter(description = "사용자의 경도", example = "126.9780", required = true)
            @RequestParam Double longitude){

        Map<String, Object> nearbyData = mapMainService.findNearbyParkingSpacesAndCameras(latitude, longitude);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, nearbyData));
    }

    @Operation(summary = "주차공간과 불법주정차 단속 구역들을 조회", description = "메인 지도에서 사용자의 위치 2km 안의 주차공간과 불법주정차 단속 구역을 조회합니다.")
    @GetMapping("/v2/map-main")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getMapWithParkingSpacesAndNoStoppingZones(
            @Parameter(description = "사용자의 위도", example = "37.5665", required = true)
            @RequestParam Double latitude,
            @Parameter(description = "사용자의 경도", example = "126.9780", required = true)
            @RequestParam Double longitude,
            @Parameter(description = "시도 이름", example = "인천광역시", required = true)
            @RequestParam String sidoName,
            @Parameter(description = "시군구 이름", example = "중구", required = true)
            @RequestParam String sigunguName){

        Map<String, Object> nearbyData = mapMainService.findNearbyParkingSpacesAndNoStoppingZones(latitude, longitude, sidoName, sigunguName);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, nearbyData));
    }

    @Operation(summary = "주차공간의 정보 조회", description = "메인 지도에서 주차공간의 간략한 정보를 조회합니다.")
    @GetMapping("/parking-space/{id}")
    public ResponseEntity<ApiResponse<ParkingSpaceSummaryResponse>> getParkingSpaceDetail(
            @Parameter(description = "조회할 주차 공간의 ID", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "사용자의 위도", example = "37.5665", required = true)
            @RequestParam Double latitude,
            @Parameter(description = "사용자의 경도", example = "126.9780", required = true)
            @RequestParam Double longitude,
            Principal principal){

        ParkingSpaceSummaryResponse response = mapMainService.getParkingSpaceDetail(id, principal, latitude, longitude);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, response));
    }

    @Operation(summary = "사용자의 현재 위치 근처 주차 공간의 정보 상세 조회", description = "사용자의 현재 위치 2km 이내 주차공간들 중 하나의 상세한 정보를 조회합니다.")
    @GetMapping("/parking-space/nearby/{id}")
    public ResponseEntity<ApiResponse<ParkingSpaceDetailResponse>> getNearbyParkingSpaceDetail(
            @Parameter(description = "조회할 주차 공간의 ID", example = "1", required = true)
            @PathVariable Long id){

        ParkingSpaceDetailResponse response = mapMainService.getParkingSpaceNearbyDetail(id);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, response));
    }

    @Operation(summary = "사용자의 현재 위치 근처 주차공간 리스트 조회", description = "사용자의 현재 위치 2km 이내 주차공간들을 가까운 순으로 조회합니다.")
    @GetMapping("/parking-space/nearby")
    public ResponseEntity<ApiResponse<ParkingSpaceNearbyResponseList>> getNearbyParkingSpaces(
            @Parameter(description = "사용자의 위도", example = "37.5665", required = true)
            @RequestParam Double latitude,
            @Parameter(description = "사용자의 경도", example = "126.9780", required = true)
            @RequestParam Double longitude){

        ParkingSpaceNearbyResponseList response = mapMainService.findNearbyParkingSpaces(latitude, longitude);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, response));
    }
}