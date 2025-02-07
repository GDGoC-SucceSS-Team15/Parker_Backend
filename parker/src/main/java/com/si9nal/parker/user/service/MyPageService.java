package com.si9nal.parker.user.service;

import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;

@Service
public class MyPageService {

    private static UserRepository userRepository;

    public MyPageService(UserRepository userRepository) {
        MyPageService.userRepository = userRepository;
    }

    @Transactional
    public MyPageUserInfoResDto getMyPageUserInfo(Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        return MyPageUserInfoResDto.fromEntity(user);
    }
}
