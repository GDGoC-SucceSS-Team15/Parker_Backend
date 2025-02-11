package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 현재 가까운 주차장 목록에서 주차장 하나를 조회하는 상세정보 DTO
 */
@Getter
@Builder
public class ParkingSpaceDetailResponse {
    private Long id;
    private String parkingName;

    private String parkingUsage; // 주차장 구분
    private String parkingType; // 주차장 유형
    private String totalParkingSpaces; // 주차장 구획 수

    private String operatingDays; // 운영요일

    // 운영시간
    private String weekdayTime;
    private String saturdayTime;
    private String holidayTime;

    private String baseParkingTime; // 기본시간
    private String baseParkingFee; // 기본 요금
    private String additionalUnitFee; // 추가요금

    private String managingAgency; // 관리기관명
    private String phoneNumber; // 전화번호

    public static ParkingSpaceDetailResponse fromEntity(ParkingSpace parkingSpace) {
        return ParkingSpaceDetailResponse.builder()
                .id(parkingSpace.getId())
                .parkingName(getValueOrDefault(parkingSpace.getParkingName()))

                .parkingUsage(parkingSpace.getParkingUsage() != null ? parkingSpace.getParkingUsage().getKorean() : "정보 없음")
                .parkingType(parkingSpace.getParkingType() != null ? parkingSpace.getParkingType().getKorean() : "정보 없음")
                .totalParkingSpaces(getValueOrDefault(parkingSpace.getTotalParkingSpaces()))

                .operatingDays(getValueOrDefault(parkingSpace.getOperatingDays()))

                .weekdayTime(formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))

                .baseParkingTime(getValueOrDefault(parkingSpace.getBaseParkingTime()))
                .baseParkingFee(getValueOrDefault(parkingSpace.getBaseParkingFee()))
                .additionalUnitFee(getValueOrDefault(parkingSpace.getAdditionalUnitFee()))

                .managingAgency(getValueOrDefault(parkingSpace.getManagingAgency()))
                .phoneNumber(getValueOrDefault(parkingSpace.getPhoneNumber()))
                .build();
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static String formatTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return "정보 없음";
        }
        return String.format("%s ~ %s", startTime.format(TIME_FORMATTER), endTime.format(TIME_FORMATTER));
    }

    private static String getValueOrDefault(Object value) {
        return (value != null && !value.toString().isEmpty()) ? value.toString() : "정보 없음";
    }
}