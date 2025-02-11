package com.si9nal.parker.report.dto.res;


import com.si9nal.parker.report.domain.Report;
import com.si9nal.parker.report.domain.enums.ApprovalStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportPostResponseDto {
    private Long id;
    private String address;
    private ApprovalStatus approvalStatus;
    private String imageUrl;

    public static ReportPostResponseDto fromEntity(Report report) {
        ReportPostResponseDto dto = new ReportPostResponseDto();
        dto.setId(report.getId());
        dto.setAddress(report.getAddress());
        dto.setApprovalStatus(report.getApprovalStatus());
        dto.setImageUrl(report.getImageUrl());
        return dto;
    }
}
