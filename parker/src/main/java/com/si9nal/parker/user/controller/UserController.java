package com.si9nal.parker.user.controller;

import com.si9nal.parker.user.dto.req.UserSignupReqDto;
import com.si9nal.parker.user.dto.res.UserInfoResDto;
import com.si9nal.parker.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResDto> SignUp(@RequestBody UserSignupReqDto request) {
        UserInfoResDto savedUser = userService.SingUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
