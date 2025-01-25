package com.si9nal.parker.user.dto.req;

import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.domain.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignupReqDto {
    private String email;
    private String phoneNumber;
    private String password;
    private String name;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .name(this.name)
                .status(Status.ACTIVE)
                .build();
    }
}
