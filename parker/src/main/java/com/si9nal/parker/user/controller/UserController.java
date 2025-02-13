package com.si9nal.parker.user.controller;

import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.user.dto.req.UserLoginReqDto;
import com.si9nal.parker.user.dto.req.UserSignupReqDto;
import com.si9nal.parker.user.dto.res.TokenDto;
import com.si9nal.parker.user.dto.res.UserInfoResDto;
import com.si9nal.parker.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@Tag(name = "유저 API", description = "회원 관련 기능을 제공하는 API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API",
            description = "회원가입 API입니다.")
    public ResponseEntity<ApiResponse<UserInfoResDto>> signUp(@RequestBody UserSignupReqDto request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.signUp(request)));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API",
            description = "로그인 API입니다. API 테스트시 해당 API를 실행한 후 발급되는 key로 로그인해주세요.")
    public ResponseEntity<ApiResponse<TokenDto>> login(@RequestBody UserLoginReqDto request) {
        return ResponseEntity.ok(ApiResponse.onSuccess(userService.login(request)));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API",
            description = "로그아웃 API입니다.")
    public ResponseEntity<ApiResponse<Void>> logout(Principal principal, HttpServletRequest request) {
        userService.logout(principal, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

    @PostMapping("/delete-user")
    @Operation(summary = "회원탈퇴 API",
            description = "회원탈퇴 API입니다.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(Principal principal, HttpServletRequest request) {
        userService.deleteUser(principal, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
