package com.si9nal.parker.user.dto.res;

import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResDto {
    private Long id;
    private String email;
    private String nickname;
    private String phoneNumber;
    private String name;
    private Status status;

    public static UserInfoResDto fromEntity(User user) {
        return UserInfoResDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .status(user.getStatus())
                .build();
    }
}
