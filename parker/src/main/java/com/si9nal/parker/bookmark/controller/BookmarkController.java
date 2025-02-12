package com.si9nal.parker.bookmark.controller;

import com.si9nal.parker.bookmark.dto.res.SpaceBookmarkResDto;
import com.si9nal.parker.bookmark.dto.res.ViolationBookmarkResDto;
import com.si9nal.parker.bookmark.service.BookmarkService;
import com.si9nal.parker.bookmark.service.BookmarkListService;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final BookmarkListService bookmarkListService;

    @PostMapping("/parking-violation/{id}")
    public ResponseEntity<ViolationBookmarkResDto> toggleViolationBookmark(

            @AuthenticationPrincipal String email,
            @PathVariable Long id
    ) {

            ViolationBookmarkResDto violationBookmarkResponse = bookmarkService.toggleViolationBookmark(email, id);
            return ResponseEntity.ok(violationBookmarkResponse);

    }

    @PostMapping("/parking-space/{id}")
    public ResponseEntity<SpaceBookmarkResDto> toggleSpaceBookmark(

            @AuthenticationPrincipal String email,
            @PathVariable Long id
    ) {

        SpaceBookmarkResDto spaceBookmarkResponse = bookmarkService.toggleSpaceBookmark(email, id);
        return ResponseEntity.ok(spaceBookmarkResponse);

    }

    @GetMapping("/parking-space-list")
    public ResponseEntity<List<ParkingSpaceMapDto>> getParkingSpaceList (
            @AuthenticationPrincipal String email
    ) {
        List<ParkingSpaceMapDto> parkingSpaceListResponse = bookmarkListService.getParkingSpaceList(email);
        return ResponseEntity.ok(parkingSpaceListResponse);
    }
}
