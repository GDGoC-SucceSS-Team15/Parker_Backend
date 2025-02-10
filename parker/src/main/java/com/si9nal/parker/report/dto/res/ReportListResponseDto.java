package com.si9nal.parker.report.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.si9nal.parker.report.domain.Report;
import com.si9nal.parker.report.domain.enums.ApprovalStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ReportListResponseDto {
    private Long id;
    private String address;
    private ApprovalStatus approvalStatus;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate createdDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime createdTime;

    public static ReportListResponseDto fromEntity(Report report) {
        ReportListResponseDto dto = new ReportListResponseDto();
        dto.setId(report.getId());
        dto.setAddress(report.getAddress());
        dto.setApprovalStatus(report.getApprovalStatus());
        dto.setCreatedDate(report.getCreatedAt().toLocalDate());
        dto.setCreatedTime(report.getCreatedAt().toLocalTime());
        return dto;
    }
}