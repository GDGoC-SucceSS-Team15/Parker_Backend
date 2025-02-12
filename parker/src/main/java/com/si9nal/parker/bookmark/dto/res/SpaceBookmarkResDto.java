package com.si9nal.parker.bookmark.dto.res;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpaceBookmarkResDto {
    private boolean isBookmarked;
    private Long parkingSpaceId;
    private String parkingName;

    public static SpaceBookmarkResDto of(boolean isBookmarked, ParkingSpace parkingSpace) {
        return new SpaceBookmarkResDto(
                isBookmarked,
                parkingSpace.getId(),
                parkingSpace.getParkingName()
        );
    }
}
