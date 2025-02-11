package com.si9nal.parker.user.service;

import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.global.common.security.TokenProvider;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.dto.req.UserLoginReqDto;
import com.si9nal.parker.user.dto.req.UserSignupReqDto;
import com.si9nal.parker.user.dto.res.TokenDto;
import com.si9nal.parker.user.dto.res.UserInfoResDto;
import com.si9nal.parker.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.time.Duration;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, RedisTemplate<String, String> redisTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public UserInfoResDto signUp(UserSignupReqDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return UserInfoResDto.fromEntity(user);
    }

    @Transactional
    public TokenDto login(UserLoginReqDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public void logout(Principal principal, HttpServletRequest request) {
        if (principal == null) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        String token = tokenProvider.resolveToken(request);
        long expirationTime = tokenProvider.getTokenExpiration(token);
        redisTemplate.opsForValue().set(token, "blacklisted", Duration.ofMillis(expirationTime));
    }

    @Transactional
    public void deleteUser(Principal principal, HttpServletRequest request) {
        if (principal == null) {
            throw new GeneralException(ErrorStatus._UNAUTHORIZED);
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        String token = tokenProvider.resolveToken(request);
        redisTemplate.delete(token);

        userRepository.delete(user);
    }
}
