package com.si9nal.parker.user.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.user.dto.req.UserLoginReqDto;
import com.si9nal.parker.user.dto.req.UserSignupReqDto;
import com.si9nal.parker.user.dto.res.TokenDto;
import com.si9nal.parker.user.dto.res.UserInfoResDto;
import com.si9nal.parker.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserInfoResDto>> signUp(@RequestBody UserSignupReqDto request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.signUp(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(@RequestBody UserLoginReqDto request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.login(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(Principal principal, HttpServletRequest request) {
        userService.logout(principal, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

    @PostMapping("/delete-user")
    public ResponseEntity<ApiResponse<Void>> deleteUser(Principal principal, HttpServletRequest request) {
        userService.deleteUser(principal, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
