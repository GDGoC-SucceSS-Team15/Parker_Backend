package com.si9nal.parker.bookmark.domain;

import com.si9nal.parker.global.common.BaseEntity;
import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import com.si9nal.parker.user.domain.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
