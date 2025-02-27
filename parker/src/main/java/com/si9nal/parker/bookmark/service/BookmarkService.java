package com.si9nal.parker.bookmark.service;

import com.si9nal.parker.bookmark.domain.SpaceBookmark;
import com.si9nal.parker.bookmark.domain.ViolationBookmark;
import com.si9nal.parker.bookmark.dto.res.SpaceBookmarkResDto;
import com.si9nal.parker.bookmark.dto.res.ViolationBookmarkResDto;
import com.si9nal.parker.bookmark.repository.SpaceBookmarkRepository;
import com.si9nal.parker.bookmark.repository.ViolationBookmarkRepository;
import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import com.si9nal.parker.parkingviolation.repository.ParkingViolationRepository;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final ViolationBookmarkRepository violationBookmarkRepository;
    private final ParkingViolationRepository parkingViolationRepository;
    private final SpaceBookmarkRepository spaceBookmarkRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final UserRepository userRepository;

    public ViolationBookmarkResDto toggleViolationBookmark(String email, Long parkingViolationId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_AUTHENTICATED));

        ParkingViolation parkingViolation = parkingViolationRepository.findById(parkingViolationId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PARKING_VIOLATION_NOT_FOUND));

        Optional<ViolationBookmark> existingBookmark = violationBookmarkRepository.findByUserAndParkingViolation(user, parkingViolation);

        if (existingBookmark.isPresent()) {
            violationBookmarkRepository.delete(existingBookmark.get());
            return ViolationBookmarkResDto.of(false, parkingViolation);
        } else {
            ViolationBookmark newBookmark = ViolationBookmark.builder()
                    .user(user)
                    .parkingViolation(parkingViolation)
                    .build();

            violationBookmarkRepository.save(newBookmark);
            return ViolationBookmarkResDto.of(true, parkingViolation);
        }
    }

    public SpaceBookmarkResDto toggleSpaceBookmark(String email, Long parkingSpaceId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        ParkingSpace parkingSpace = parkingSpaceRepository.findById(parkingSpaceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PARKING_SPACE_NOT_FOUND));

        Optional<SpaceBookmark> existingBookmark = spaceBookmarkRepository.findByUserAndParkingSpace(user, parkingSpace);

        if (existingBookmark.isPresent()) {
            spaceBookmarkRepository.delete(existingBookmark.get());
            return SpaceBookmarkResDto.of(false, parkingSpace);
        } else {
            SpaceBookmark newSpaceBookmark = SpaceBookmark.builder()
                    .user(user)
                    .parkingSpace(parkingSpace)
                    .build();

            spaceBookmarkRepository.save(newSpaceBookmark);
            return SpaceBookmarkResDto.of(true, parkingSpace);
        }
    }

}
