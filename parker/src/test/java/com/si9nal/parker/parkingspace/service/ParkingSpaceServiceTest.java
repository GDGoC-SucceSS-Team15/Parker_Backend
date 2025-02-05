package com.si9nal.parker.parkingspace.service;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.fixture.TestFixture;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ParkingSpaceServiceTest {
    @Mock
    private ParkingSpaceRepository repository;

    @InjectMocks
    private ParkingSpaceService service;

    private ParkingSpace sampleParkingSpace;

    @BeforeEach
    void setUp() {
        sampleParkingSpace = TestFixture.createSampleParkingSpace();
    }

    @Test
    @DisplayName("반경 내 주차장 검색 테스트")
    void getParkingSpacesTest() throws Exception {
        // given
        Double latitude = 37.5665;
        Double longitude = 126.9780;
        Double distance = 2.0;
        List<ParkingSpace> expectedParkingSpaces = Arrays.asList(sampleParkingSpace);

        when(repository.findWithinRectangle(any())).thenReturn(expectedParkingSpaces);

        // when
        List<ParkingSpace> result = service.getParkingSpaces(latitude, longitude, distance);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParkingName()).isEqualTo("테스트 주차장");
    }

//    @Test
//    @DisplayName("잘못된 좌표로 주차장 검색 시 예외 처리 테스트")
//    void getParkingSpacesWithInvalidCoordinatesTest() {
//        // given
//        Double invalidLatitude = 91.0; // 유효하지 않은 위도
//        Double longitude = 126.9780;
//        Double distance = 2.0;
//
//        // claude 참고해서 코드 리팩토링 필요. service에서 예외처리. 유효성검사 등
//        // when & then
//        assertThrows(IllegalArgumentException.class, () ->
//                service.getParkingSpaces(invalidLatitude, longitude, distance)
//        );
//    }
}