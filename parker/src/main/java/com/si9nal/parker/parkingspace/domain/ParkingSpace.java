package com.si9nal.parker.parkingspace.domain;

import com.si9nal.parker.global.common.BaseEntity;
import com.si9nal.parker.parkingspace.domain.enums.FeeType;
import com.si9nal.parker.parkingspace.domain.enums.ParkingType;
import com.si9nal.parker.parkingspace.domain.enums.ParkingUsage;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "parking_space")
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

    private Double latitude;

    private Double longitude;

    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
    private Point point;


    @Column(length = 15)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @Enumerated(EnumType.STRING)
    private ParkingUsage parkingUsage;

    @Builder
    public ParkingSpace(String parkingName, String operatingDays, LocalTime weekdayStartTime,
                        LocalTime weekdayEndTime, LocalTime saturdayStartTime, LocalTime saturdayEndTime,
                        LocalTime holidayStartTime, LocalTime holidayEndTime, Integer baseParkingTime,
                        Integer baseParkingFee, Integer additionalUnitTime, Integer additionalUnitFee,
                        Integer totalParkingSpaces, String managingAgency, String address, Double latitude,
                        Double longitude, String phoneNumber, ParkingType parkingType, FeeType feeType,
                        ParkingUsage parkingUsage) {
        this.parkingName = parkingName;
        this.operatingDays = operatingDays;
        this.weekdayStartTime = weekdayStartTime;
        this.weekdayEndTime = weekdayEndTime;
        this.saturdayStartTime = saturdayStartTime;
        this.saturdayEndTime = saturdayEndTime;
        this.holidayStartTime = holidayStartTime;
        this.holidayEndTime = holidayEndTime;
        this.baseParkingTime = baseParkingTime;
        this.baseParkingFee = baseParkingFee;
        this.additionalUnitTime = additionalUnitTime;
        this.additionalUnitFee = additionalUnitFee;
        this.totalParkingSpaces = totalParkingSpaces;
        this.managingAgency = managingAgency;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.parkingType = parkingType;
        this.feeType = feeType;
        this.parkingUsage = parkingUsage;

        try {
            updateLocation(latitude, longitude);
        } catch (Exception e) {
            // 로깅 추가 고려
            this.point = null;
        }
    }

    public void updateLocation(Double latitude, Double longitude) throws Exception {
        this.latitude = latitude;
        this.longitude = longitude;
        if (latitude != null && longitude != null) {
            Point newPoint = (Point) new org.locationtech.jts.io.WKTReader()
                    .read(String.format("POINT(%f %f)", latitude, longitude));
            newPoint.setSRID(4326);
            this.point = newPoint;
        } else {
            this.point = null;
        }
    }
}