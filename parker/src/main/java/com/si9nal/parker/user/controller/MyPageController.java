package com.si9nal.parker.user.controller;

import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api/me")
public class MyPageController {
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<MyPageUserInfoResDto> getUserInfo(Principal principal) {
        MyPageUserInfoResDto myPageUserInfo = myPageService.getMyPageUserInfo(principal);
        return ResponseEntity.ok()
                .body(myPageUserInfo);
    }
}
