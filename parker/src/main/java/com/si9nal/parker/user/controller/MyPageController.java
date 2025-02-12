package com.si9nal.parker.user.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

@RestController
@RequestMapping("/api/my-page")
@Tag(name = "마이페이지 API", description = "개인정보 관련 마이페이지 기능을 제공하는 API")
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/user-info")
    @Operation(summary = "마이페이지 메인 API",
            description = "회원 정보 조회 API입니다.")
    public ApiResponse<MyPageUserInfoResDto> getUserInfo(Principal principal) {
        return ApiResponse.onSuccess(myPageService.getMyPageUserInfo(principal));
    }

    @PostMapping(value = "/user-info", consumes = {"multipart/form-data"})
    @Operation(summary = "개인 정보 수정 API",
            description = "프로필 사진과 닉네임을 수정하는 API입니다.")
    public ApiResponse<MyPageUserInfoResDto> updateUserInfo(
            Principal principal,
            @RequestPart(value = "nickname", required = false) String nickname,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ApiResponse.onSuccess(myPageService.updateMyPageUserInfo(principal, nickname, file));
    }
}
