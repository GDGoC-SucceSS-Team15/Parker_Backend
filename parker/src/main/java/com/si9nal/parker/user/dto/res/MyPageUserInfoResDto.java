package com.si9nal.parker.user.dto.res;

import com.si9nal.parker.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageUserInfoResDto {
    private Long id;
    private String name;
    private String nickname;
    private String profileImageUrl;

    public static MyPageUserInfoResDto fromEntity(User user) {
        return MyPageUserInfoResDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
