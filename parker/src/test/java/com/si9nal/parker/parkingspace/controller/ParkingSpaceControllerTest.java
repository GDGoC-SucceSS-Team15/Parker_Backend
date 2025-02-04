package com.si9nal.parker.parkingspace.controller;

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
import org.springframework.http.MediaType;
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
    void getNearbyParkingSpacesSuccessTest() throws Exception {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;
        List<ParkingSpace> parkingSpaces = Arrays.asList(sampleParkingSpace);

        when(service.getParkingSpaces(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(parkingSpaces);

        // when
        ResponseEntity<List<ParkingSpaceMapDto>> response =
                controller.getNearbyParkingSpaces(latitude, longitude);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("주변 주차장 조회 API 테스트 - 결과 없음")
    void getNearbyParkingSpacesEmptyResultTest() throws Exception {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;

        when(service.getParkingSpaces(anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(Collections.emptyList());

        // when
        ResponseEntity<List<ParkingSpaceMapDto>> response =
                controller.getNearbyParkingSpaces(latitude, longitude);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    @DisplayName("주변 주차장 조회 API 테스트 - 서버 에러")
    void getNearbyParkingSpacesServerErrorTest() throws Exception {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;

        when(service.getParkingSpaces(anyDouble(), anyDouble(), anyDouble()))
                .thenThrow(new RuntimeException("서버 에러"));

        // when
        ResponseEntity<List<ParkingSpaceMapDto>> response =
                controller.getNearbyParkingSpaces(latitude, longitude);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody()).isEmpty();
    }
}