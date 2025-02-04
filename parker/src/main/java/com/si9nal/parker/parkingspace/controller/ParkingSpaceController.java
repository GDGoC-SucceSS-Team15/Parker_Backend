package com.si9nal.parker.parkingspace.controller;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import com.si9nal.parker.parkingspace.service.ParkingSpaceService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/parkingSpace")
public class ParkingSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpaceService.class);
    private final ParkingSpaceService service;

    public ParkingSpaceController(ParkingSpaceService service) {
        this.service = service;
    }

    @GetMapping("/map")
    public ResponseEntity<List<ParkingSpaceMapDto>> getNearbyParkingSpaces(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        try {
            List<ParkingSpace> parkingSpaces = service.getParkingSpaces(latitude, longitude, 2.0); // 2km 반경
            if (parkingSpaces.isEmpty()) {
                return ResponseEntity.status(404).body(Collections.emptyList());
            }
            // ParkingSpace 엔티티 리스트를 DTO 리스트로 변환
            List<ParkingSpaceMapDto> parkingSpaceDtos = parkingSpaces.stream()
                    .map(ParkingSpaceMapDto::fromEntity)
                    .toList();

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(parkingSpaceDtos);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input parameters: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.emptyList());
        } catch (Exception e) {
            logger.error("Error fetching parking spaces", e);
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

}
