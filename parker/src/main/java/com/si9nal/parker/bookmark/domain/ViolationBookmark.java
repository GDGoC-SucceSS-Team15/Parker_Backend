package com.si9nal.parker.bookmark.domain;

import com.si9nal.parker.global.common.BaseEntity;
import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import com.si9nal.parker.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViolationBookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_violation_id", nullable = false)
    private ParkingViolation parkingViolation;


}
