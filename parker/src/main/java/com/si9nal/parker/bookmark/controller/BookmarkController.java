package com.si9nal.parker.bookmark.controller;

import com.si9nal.parker.bookmark.dto.res.SpaceBookmarkResDto;
import com.si9nal.parker.bookmark.dto.res.ViolationBookmarkResDto;
import com.si9nal.parker.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

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
}
