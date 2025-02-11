package com.si9nal.parker.user.service;

import com.si9nal.parker.global.common.s3.S3Service;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;

@Service
public class MyPageService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    public MyPageService(UserRepository userRepository, S3Service s3Service) {
        this.userRepository = userRepository;
        this.s3Service = s3Service;
    }

    @Transactional(readOnly = true)
    public MyPageUserInfoResDto getMyPageUserInfo(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        return MyPageUserInfoResDto.fromEntity(user);
    }

    @Transactional
    public MyPageUserInfoResDto updateMyPageUserInfo(Principal principal, String nickname, MultipartFile file) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));


        if (nickname != null && !nickname.trim().isEmpty() && !nickname.equals(user.getNickname())) {
            user.setNickname(nickname.trim());
        }

        if (file != null && !file.isEmpty()) {
            try {
                if (user.getProfileImageUrl() != null) {
                    s3Service.deleteFile(user.getProfileImageUrl());
                }
                String newImageUrl = s3Service.uploadFile(file);
                user.setProfileImageUrl(newImageUrl);
            } catch (Exception e) {
                throw new RuntimeException("파일 업로드 중 오류 발생: " + e.getMessage(), e);
            }
        }

        return MyPageUserInfoResDto.fromEntity(user);
    }
}
