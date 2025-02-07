package com.si9nal.parker.user.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMyPageUserInfoReqDto {
    private String nickname;
    private String profileImageUrl;
}
