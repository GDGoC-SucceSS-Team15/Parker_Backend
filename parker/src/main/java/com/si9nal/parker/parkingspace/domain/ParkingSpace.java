package com.si9nal.parker.parkingspace.domain;

import com.si9nal.parker.global.common.BaseEntity;
import com.si9nal.parker.parkingspace.domain.enums.FeeType;
import com.si9nal.parker.parkingspace.domain.enums.ParkingType;
import com.si9nal.parker.parkingspace.domain.enums.ParkingUsage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingSpace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String parkingName;

    private String operatingDays;

    private LocalTime weekdayStartTime;
    private LocalTime weekdayEndTime;
    private LocalTime saturdayStartTime;
    private LocalTime saturdayEndTime;
    private LocalTime holidayStartTime;
    private LocalTime holidayEndTime;

    private Integer baseParkingTime;
    private Integer baseParkingFee;
    private Integer additionalUnitTime;
    private Integer additionalUnitFee;

    private Integer totalParkingSpaces;

    private String managingAgency;


    @Column(nullable = false)
    private String address;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @Enumerated(EnumType.STRING)
    private ParkingUsage parkingUsage;


}