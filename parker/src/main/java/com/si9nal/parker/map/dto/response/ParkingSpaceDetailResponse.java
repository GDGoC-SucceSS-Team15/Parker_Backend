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
                .parkingName(parkingSpace.getParkingName() != null && !parkingSpace.getParkingName().isEmpty() ? parkingSpace.getParkingName() : "정보 없음")

                .parkingUsage(parkingSpace.getParkingUsage() != null ? parkingSpace.getParkingUsage().getKorean() : "정보 없음")
                .parkingType(parkingSpace.getParkingType() != null ? parkingSpace.getParkingType().getKorean() : "정보 없음")
                .totalParkingSpaces(parkingSpace.getTotalParkingSpaces() != null ? parkingSpace.getTotalParkingSpaces().toString() : "정보 없음")

                .operatingDays(parkingSpace.getOperatingDays() != null && !parkingSpace.getOperatingDays().isEmpty() ? parkingSpace.getOperatingDays() : "정보 없음")

                .weekdayTime(formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))

                .baseParkingTime(parkingSpace.getBaseParkingTime() != null ? parkingSpace.getBaseParkingTime().toString() : "정보 없음")
                .baseParkingFee(parkingSpace.getBaseParkingFee() != null ? parkingSpace.getBaseParkingFee().toString() : "정보 없음")
                .additionalUnitFee(parkingSpace.getAdditionalUnitFee() != null ? parkingSpace.getAdditionalUnitFee().toString() : "정보 없음")

                .managingAgency(parkingSpace.getManagingAgency() != null && !parkingSpace.getManagingAgency().isEmpty() ? parkingSpace.getManagingAgency() : "정보 없음")
                .phoneNumber(parkingSpace.getPhoneNumber() != null && !parkingSpace.getPhoneNumber().isEmpty() ? parkingSpace.getPhoneNumber() : "정보 없음")
                .build();
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private static String formatTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return "정보 없음";
        }
        return String.format("%s ~ %s", startTime.format(TIME_FORMATTER), endTime.format(TIME_FORMATTER));
    }
}