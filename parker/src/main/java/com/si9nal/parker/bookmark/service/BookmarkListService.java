package com.si9nal.parker.bookmark.service;

import com.si9nal.parker.bookmark.domain.SpaceBookmark;
import com.si9nal.parker.bookmark.repository.SpaceBookmarkRepository;
import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkListService {
    private final SpaceBookmarkRepository spaceBookmarkRepository;
    private final UserRepository userRepository;

    public List<ParkingSpaceMapDto> getParkingSpaceList(String email, String sort) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<SpaceBookmark> spaceBookmarks;

        if("latest".equals(sort)) {
            spaceBookmarks = spaceBookmarkRepository.findByUserOrderByCreatedAtDesc(user);
        } else if ("earliest".equals(sort)) {
            spaceBookmarks = spaceBookmarkRepository.findByUserOrderByCreatedAtAsc(user);
        } else {
            throw new GeneralException(ErrorStatus._BAD_REQUEST);
        }

        return spaceBookmarks.stream()
                .map(SpaceBookmark -> ParkingSpaceMapDto.fromEntity(SpaceBookmark.getParkingSpace()))
                .collect(Collectors.toList());
    }
}
