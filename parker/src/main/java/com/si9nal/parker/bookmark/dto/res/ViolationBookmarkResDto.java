package com.si9nal.parker.bookmark.dto.res;

import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ViolationBookmarkResDto {
    private boolean isBookmarked;
    private Long parkingViolationId;
    private String detailedLocation;

    public static ViolationBookmarkResDto of(boolean isBookmarked, ParkingViolation parkingViolation) {
        return new ViolationBookmarkResDto(
                isBookmarked,
                parkingViolation.getId(),
                parkingViolation.getDetailedLocation()
        );
    }
}
