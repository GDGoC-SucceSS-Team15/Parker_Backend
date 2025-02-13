package com.si9nal.parker.bookmark.service;

import com.si9nal.parker.bookmark.domain.SpaceBookmark;
import com.si9nal.parker.bookmark.repository.SpaceBookmarkRepository;
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
                .orElseThrow(() -> new EntityNotFoundException("사용자 조회에 실패했습니다."));

        List<SpaceBookmark> spaceBookmarks;

        if("earliest".equals(sort)) {
            spaceBookmarks = spaceBookmarkRepository.findByUserOrderByCreatedAtAsc(user);
        } else if ("latest".equals(sort)) {
            spaceBookmarks = spaceBookmarkRepository.findByUserOrderByCreatedAtDesc(user);
        } else {
            spaceBookmarks = spaceBookmarkRepository.findByUser(user); // 에러 추가 예정
        }

        return spaceBookmarks.stream()
                .map(SpaceBookmark -> ParkingSpaceMapDto.fromEntity(SpaceBookmark.getParkingSpace()))
                .collect(Collectors.toList());
    }
}
