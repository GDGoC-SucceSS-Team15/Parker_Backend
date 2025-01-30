package com.si9nal.parker.parkingvioation.dto.res;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ParkingViolationDetailResponseDto {
    private String sidoName;
    private String sigunguName;
    private String roadName;
    private String detailedLocation;
    private String managementPhoneNumber;
    private String weekdayTime;
    private String saturdayTime;
    private String holidayTime;
}
