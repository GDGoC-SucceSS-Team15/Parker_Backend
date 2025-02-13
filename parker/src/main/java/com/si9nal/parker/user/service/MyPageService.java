package com.si9nal.parker.user.service;

import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.s3.AmazonS3Manager;
import com.si9nal.parker.global.common.s3.Uuid;
import com.si9nal.parker.global.common.s3.UuidRepository;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.dto.res.MyPageUserInfoResDto;
import com.si9nal.parker.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    public MyPageService(UserRepository userRepository, AmazonS3Manager s3Manager, UuidRepository uuidRepository) {
        this.userRepository = userRepository;
        this.s3Manager = s3Manager;
        this.uuidRepository = uuidRepository;
    }


    public MyPageUserInfoResDto getMyPageUserInfo(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        return MyPageUserInfoResDto.fromEntity(user);
    }

    @Transactional
    public MyPageUserInfoResDto updateMyPageUserInfo(Principal principal, String nickname, MultipartFile file) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        if (nickname != null && !nickname.trim().isEmpty() && !nickname.equals(user.getNickname())) {
            user.setNickname(nickname.trim());
        }

        if (file != null && !file.isEmpty()) {
            try {
                if (user.getProfileImageUrl() != null) {
                    s3Manager.deleteFile(user.getProfileImageUrl());
                }
                Uuid imgUuid = uuidRepository.save(Uuid.builder().uuid(UUID.randomUUID().toString()).build());
                String imgURL = s3Manager.uploadFile(s3Manager.generateProfileKeyName(imgUuid), file);
                user.setProfileImageUrl(imgURL);
            } catch (Exception e) {
                throw new GeneralException(ErrorStatus.FILE_UPLOAD_FAILED);
            }
        }

        return MyPageUserInfoResDto.fromEntity(user);
    }
}
