package com.si9nal.parker.map.controller;

import com.si9nal.parker.map.service.MapMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}