package com.si9nal.parker.parkingspace.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import com.si9nal.parker.parkingspace.fixture.TestFixture;
import com.si9nal.parker.parkingspace.service.ParkingSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ParkingSpaceControllerTest {
    @Mock
    private ParkingSpaceService service;

    @InjectMocks
    private ParkingSpaceController controller;

    private ParkingSpace sampleParkingSpace;

    @BeforeEach
    void setUp() {
        sampleParkingSpace = TestFixture.createSampleParkingSpace();
    }

    @Test
    @DisplayName("주변 주차장 조회 API 테스트 - 성공 케이스")
    void getNearbyParkingSpacesSuccessTest() {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;
        List<ParkingSpace> parkingSpaces = Arrays.asList(sampleParkingSpace);

        when(service.getParkingSpaces(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(parkingSpaces);

        // when
        ResponseEntity<ApiResponse<List<ParkingSpaceMapDto>>> response =
                controller.getNearbyParkingSpaces(latitude, longitude);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResult()).hasSize(1);
        assertThat(response.getBody().getIsSuccess()).isTrue();
    }

    @Test
    @DisplayName("주변 주차장 조회 API 테스트 - 결과 없음")
    void getNearbyParkingSpacesEmptyResultTest() {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;

        when(service.getParkingSpaces(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(Collections.emptyList());

        // when
        ResponseEntity<ApiResponse<List<ParkingSpaceMapDto>>> response =
                controller.getNearbyParkingSpaces(latitude, longitude);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getResult()).isEmpty();
        assertThat(response.getBody().getIsSuccess()).isTrue();
    }
}