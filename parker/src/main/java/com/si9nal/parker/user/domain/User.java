package com.si9nal.parker.user.domain;

import com.si9nal.parker.global.common.BaseEntity;
import com.si9nal.parker.user.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    private String nickname;

    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
}
