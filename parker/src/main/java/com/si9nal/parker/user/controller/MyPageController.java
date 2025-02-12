package com.si9nal.parker.user.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.service.MyPageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

@RestController
@RequestMapping("/api/my-page")
public class MyPageController {

    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/user-info")
    public ApiResponse<MyPageUserInfoResDto> getUserInfo(Principal principal) {
        return ApiResponse.onSuccess(myPageService.getMyPageUserInfo(principal));
    }

    @PostMapping(value = "/user-info", consumes = {"multipart/form-data"})
    public ApiResponse<MyPageUserInfoResDto> updateUserInfo(
            Principal principal,
            @RequestPart(value = "nickname", required = false) String nickname,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ApiResponse.onSuccess(myPageService.updateMyPageUserInfo(principal, nickname, file));
    }
}
