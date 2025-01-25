package com.si9nal.parker.user.service;

import com.si9nal.parker.global.common.security.TokenProvider;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.dto.req.UserLoginReqDto;
import com.si9nal.parker.user.dto.req.UserSignupReqDto;
import com.si9nal.parker.user.dto.res.TokenDto;
import com.si9nal.parker.user.dto.res.UserInfoResDto;
import com.si9nal.parker.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public UserInfoResDto SingUp(UserSignupReqDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        User user = request.toEntity();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return UserInfoResDto.fromEntity(user);
    }

    @Transactional
    public TokenDto Login(UserLoginReqDto request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀립니다.");
        }

        String accessToken = tokenProvider.createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
