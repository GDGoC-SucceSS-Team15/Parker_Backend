package com.si9nal.parker.camera.domain;

import com.si9nal.parker.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CameraLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    private Double latitude;

    private Double longitude;

    @Column(nullable = true, columnDefinition = "POINT SRID 4326")
    private Point point;

    @Column(nullable = false)
    private String borough;

    @Column(nullable = false)
    private String areaName;

    @Column(nullable = false)
    private String classification;
}
