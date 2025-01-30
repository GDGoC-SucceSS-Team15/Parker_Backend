package com.si9nal.parker.parkingvioation.domain;

import com.si9nal.parker.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ParkingViolation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String sidoName;

    @Column(nullable = false, length = 100)
    private String sigunguName;

    private String roadName;

    @Column(length = 500)
    private String detailedLocation;

    @Column(length = 15)
    private String managementPhoneNumber;

    private LocalTime weekdayStartTime;
    private LocalTime weekdayEndTime;
    private LocalTime saturdayStartTime;
    private LocalTime saturdayEndTime;
    private LocalTime holidayStartTime;
    private LocalTime holidayEndTime;

}
