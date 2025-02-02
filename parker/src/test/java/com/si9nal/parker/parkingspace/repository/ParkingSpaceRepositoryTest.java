package com.si9nal.parker.parkingspace.repository;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.fixture.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ParkingSpaceRepositoryTest {
    @Test
    @DisplayName("주차장 위치 업데이트 테스트")
    void updateLocationTest() throws Exception {
        // given
        ParkingSpace parkingSpace = TestFixture.createSampleParkingSpace();
        Double newLatitude = 37.5665;
        Double newLongitude = 126.9780;

        // when
        parkingSpace.updateLocation(newLatitude, newLongitude);

        // then
        assertThat(parkingSpace.getLatitude()).isEqualTo(newLatitude);
        assertThat(parkingSpace.getLongitude()).isEqualTo(newLongitude);
        assertThat(parkingSpace.getPoint()).isNotNull();
        assertThat(parkingSpace.getPoint().getSRID()).isEqualTo(4326);
    }
}