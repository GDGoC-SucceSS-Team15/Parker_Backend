package com.si9nal.parker.camera.controller;

import com.si9nal.parker.camera.service.CameraLocationService;
import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.global.common.apiPayload.code.status.SuccessStatus;
import com.si9nal.parker.map.dto.response.CameraLocationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "메인 지도 API", description = "메인 지도화면에서 주차장, 주정차금지구역 조회를 제공하는 API")
@RestController
@RequestMapping("/api/parker")
@RequiredArgsConstructor
public class CameraLocationController {

    private final CameraLocationService cameraLocationService;

    @Operation(summary = "단속 카메라 위치 조회", description = "메인 지도에서 사용자의 위치 2km 안의 단속카메라의 위치를 불러옵니다.")
    @GetMapping("/camera-location-map")
    public ResponseEntity<ApiResponse<List<CameraLocationResponse>>> getMapWithCameraLocation (
            @Parameter(description = "사용자의 위도", example = "37.5358", required = true)
            @RequestParam Double latitude,
            @Parameter(description = "사용자의 경도", example = "126.8705", required = true)
            @RequestParam Double longitude) {

        List<CameraLocationResponse> nearbyCamera = cameraLocationService.findNearbyCameras(latitude, longitude);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, nearbyCamera)
        );

    }
}
