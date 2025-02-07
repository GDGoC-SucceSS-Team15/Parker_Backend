package com.si9nal.parker.user.service;

import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.dto.req.UpdateMyPageUserInfoReqDto;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.function.Consumer;

@Service
public class MyPageService {

    private final UserRepository userRepository;

    public MyPageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public MyPageUserInfoResDto getMyPageUserInfo(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        return MyPageUserInfoResDto.fromEntity(user);
    }

    @Transactional
    public MyPageUserInfoResDto updateMyPageUserInfo(Principal principal, UpdateMyPageUserInfoReqDto dto) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        updateFieldIfPresent(dto.getNickname(), user::setNickname);
        updateFieldIfPresent(dto.getProfileImageUrl(), user::setProfileImageUrl);

        return MyPageUserInfoResDto.fromEntity(user);
    }

    private void updateFieldIfPresent(String value, Consumer<String> updater) {
        if (value != null && !value.trim().isEmpty()) {
            updater.accept(value.trim());
        }
    }
}
