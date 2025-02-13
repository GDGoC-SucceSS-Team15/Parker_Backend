package com.si9nal.parker.map.dto.response;

import com.si9nal.parker.global.common.util.DtoFormatUtil;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;

import lombok.Builder;
import lombok.Getter;


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
                .parkingName(DtoFormatUtil.getValueOrDefault(parkingSpace.getParkingName()))

                .parkingUsage(parkingSpace.getParkingUsage() != null ? parkingSpace.getParkingUsage().getKorean() : "정보 없음")
                .parkingType(parkingSpace.getParkingType() != null ? parkingSpace.getParkingType().getKorean() : "정보 없음")
                .totalParkingSpaces(DtoFormatUtil.getValueOrDefault(parkingSpace.getTotalParkingSpaces()))

                .operatingDays(DtoFormatUtil.getValueOrDefault(parkingSpace.getOperatingDays()))

                .weekdayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getWeekdayStartTime(), parkingSpace.getWeekdayEndTime()))
                .saturdayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getSaturdayStartTime(), parkingSpace.getSaturdayEndTime()))
                .holidayTime(DtoFormatUtil.formatTimeRange(parkingSpace.getHolidayStartTime(), parkingSpace.getHolidayEndTime()))

                .baseParkingTime(DtoFormatUtil.getValueOrDefault(parkingSpace.getBaseParkingTime()))
                .baseParkingFee(DtoFormatUtil.getValueOrDefault(parkingSpace.getBaseParkingFee()))
                .additionalUnitFee(DtoFormatUtil.getValueOrDefault(parkingSpace.getAdditionalUnitFee()))

                .managingAgency(DtoFormatUtil.getValueOrDefault(parkingSpace.getManagingAgency()))
                .phoneNumber(DtoFormatUtil.getValueOrDefault(parkingSpace.getPhoneNumber()))
                .build();
    }


}