package com.si9nal.parker.map.controller;

import com.si9nal.parker.map.dto.response.ParkingSpaceDetailResponse;
import com.si9nal.parker.map.dto.response.ParkingSpaceSummaryResponse;
import com.si9nal.parker.map.service.MapMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/parker")
@RequiredArgsConstructor
public class MapMainController {

    private final MapMainService mapMainService;

    @GetMapping("/v1/map-main")
    public ResponseEntity<Map<String, Object>> getMapWithParkingSpacesAndCameraLocation(
            @RequestParam Double latitude,
            @RequestParam Double longitude){

        Map<String, Object> nearbyData = mapMainService.findNearbyParkingSpacesAndCameras(latitude, longitude);

        return ResponseEntity.ok(nearbyData);
    }

    @GetMapping("/v2/map-main")
    public ResponseEntity<Map<String, Object>> getMapWithParkingSpacesAndNoStoppingZones(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String sigunguName){

        Map<String, Object> nearbyData = mapMainService.findNearbyParkingSpacesAndNoStoppingZones(latitude, longitude, sigunguName);

        return ResponseEntity.ok(nearbyData);
    }

    @GetMapping("/parking-space/{id}")
    public ResponseEntity<ParkingSpaceSummaryResponse> getParkingSpaceDetail(
            @PathVariable Long id,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            Principal principal){

        ParkingSpaceSummaryResponse response = mapMainService.getParkingSpaceDetail(id, principal, latitude, longitude);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/parking-space/nearby/{id}")
    public ResponseEntity<ParkingSpaceDetailResponse> getNearbyParkingSpaceDetail(
            @PathVariable Long id){

        ParkingSpaceDetailResponse response = mapMainService.getParkingSpaceNearbyDetail(id);

        return ResponseEntity.ok(response);
    }
}