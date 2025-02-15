package com.si9nal.parker.bookmark.controller;

import com.si9nal.parker.bookmark.dto.res.SpaceBookmarkResDto;
import com.si9nal.parker.bookmark.dto.res.ViolationBookmarkResDto;
import com.si9nal.parker.bookmark.service.BookmarkService;
import com.si9nal.parker.bookmark.service.BookmarkListService;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "즐겨찾기 API", description = "즐겨찾기 추가, 삭제, 조회 API")
@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final BookmarkListService bookmarkListService;

    @Operation(summary = "불법주정차 단속 구역 즐겨찾기 추가 및 삭제", description = "데이터가 존재하면 삭제, 데이터가 존재하지 않으면 추가합니다.")
    @PostMapping("/parking-violation/{id}")
    public ResponseEntity<ViolationBookmarkResDto> toggleViolationBookmark(
            @AuthenticationPrincipal String email,
            @Parameter (description = "즐겨찾기를 추가/삭제 할 불법주정차 단속 구역 ID", example = "1")
            @PathVariable Long id
    ) {
            ViolationBookmarkResDto violationBookmarkResponse = bookmarkService.toggleViolationBookmark(email, id);
            return ResponseEntity.ok(violationBookmarkResponse);
    }

    @Operation(summary = "주차공간 즐겨찾기 추가 및 삭제", description = "데이터가 존재하면 삭제, 데이터가 존재하지 않으면 추가합니다.")
    @PostMapping("/parking-space/{id}")
    public ResponseEntity<SpaceBookmarkResDto> toggleSpaceBookmark(

            @AuthenticationPrincipal String email,
            @Parameter (description = "즐겨찾기를 추가/삭제 할 주차공간 ID", example = "1")
            @PathVariable Long id
    ) {
        SpaceBookmarkResDto spaceBookmarkResponse = bookmarkService.toggleSpaceBookmark(email, id);
        return ResponseEntity.ok(spaceBookmarkResponse);
    }

    @Operation(summary = "즐겨찾기 한 주차공간 조회", description = "최신순, 오래된순 으로 조회할 수 있으며 요청 파라미터 없을 시 기본 최신순으로 조회")
    @GetMapping("/parking-space-list")
    public ResponseEntity<List<ParkingSpaceMapDto>> getParkingSpaceList (
            @AuthenticationPrincipal String email,
            @Parameter(description = "정렬 방법", examples = {
                    @ExampleObject(name = "최신순", value = "latest"),
                    @ExampleObject(name = "오래된순", value = "earliest")
                }
            )
            @RequestParam(required = false, defaultValue = "latest") String sort
    ) {
        List<ParkingSpaceMapDto> parkingSpaceListResponse = bookmarkListService.getParkingSpaceList(email, sort);
        return ResponseEntity.ok(parkingSpaceListResponse);
    }
}
