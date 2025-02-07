package com.si9nal.parker.user.controller;

import com.si9nal.parker.user.dto.req.UpdateMyPageUserInfoReqDto;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/my-page")
public class MyPageController {
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<MyPageUserInfoResDto> getUserInfo(Principal principal) {
        MyPageUserInfoResDto myPageUserInfo = myPageService.getMyPageUserInfo(principal);
        return ResponseEntity.ok(myPageUserInfo);
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<MyPageUserInfoResDto> updateUserInfo(Principal principal, @RequestBody UpdateMyPageUserInfoReqDto dto) {
        MyPageUserInfoResDto updatedMyPageUserInfo = myPageService.updateMyPageUserInfo(principal, dto);
        return ResponseEntity.ok(updatedMyPageUserInfo);
    }
}
