package com.si9nal.parker.user.controller;

import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.service.MyPageService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MyPageUserInfoResDto> getUserInfo(Principal principal) {
        MyPageUserInfoResDto myPageUserInfo = myPageService.getMyPageUserInfo(principal);
        return ResponseEntity.ok(myPageUserInfo);
    }

    @PostMapping(value = "/user-info", consumes = {"multipart/form-data"})
    public ResponseEntity<MyPageUserInfoResDto> updateUserInfo(Principal principal, @RequestPart(value = "nickname", required = false) String nickname, @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.ok(myPageService.updateMyPageUserInfo(principal, nickname, file));
    }
}
