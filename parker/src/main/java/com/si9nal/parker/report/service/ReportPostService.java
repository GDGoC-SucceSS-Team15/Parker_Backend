package com.si9nal.parker.report.service;

import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.global.common.s3.AmazonS3Manager;
import com.si9nal.parker.global.common.s3.Uuid;
import com.si9nal.parker.global.common.s3.UuidRepository;
import com.si9nal.parker.report.domain.Report;
import com.si9nal.parker.report.domain.enums.ApprovalStatus;

import com.si9nal.parker.report.dto.req.ReportPostRequestDto;
import com.si9nal.parker.report.dto.res.ReportListResponseDto;
import com.si9nal.parker.report.dto.res.ReportPostResponseDto;
import com.si9nal.parker.report.repository.ReportRepository;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true) // 클래스 레벨에 readOnly = true 설정
public class ReportPostService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    public ReportPostService(ReportRepository reportRepository, UserRepository userRepository, AmazonS3Manager s3Manager, UuidRepository uuidRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.s3Manager = s3Manager;
        this.uuidRepository = uuidRepository;
    }

    @Transactional // 쓰기 작업이 필요
    public ReportPostResponseDto createReport(String email, ReportPostRequestDto requestDto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // Create report
        Report report = requestDto.toEntity();
        report.setUser(user);

        if (requestDto.hasImage()) {
            Uuid imgUuid = uuidRepository.save(Uuid.builder().uuid(UUID.randomUUID().toString()).build());
            String imgURL = s3Manager.uploadFile(s3Manager.generateReportKeyName(imgUuid), requestDto.getImage());
            report.setImageUrl(imgURL);
        }

        // Save and return
        Report savedReport = reportRepository.save(report);
        return ReportPostResponseDto.fromEntity(savedReport);
    }


    public List<ReportListResponseDto> getMyReports(String email) {
        List<Report> reports = reportRepository.findByUserEmailOrderByCreatedAtDesc(email);
        return reports.stream()
                .map(ReportListResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional // 삭제 작업이 필요
    public void withdrawReport(String email, Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.REPORT_NOT_FOUND));

        // 본인 신고인지 확인
        if (!report.getUser().getEmail().equals(email)) {
            throw new GeneralException(ErrorStatus.REPORT_UNAUTHORIZED);
        }

        // 이미 처리된 신고는 철회 불가
        if (report.getApprovalStatus() != ApprovalStatus.PENDING) {
            throw new GeneralException(ErrorStatus.REPORT_ALREADY_PROCESSED);
        }

        if (report.getImageUrl() != null) {
            s3Manager.deleteFile(report.getImageUrl());
        }
        reportRepository.delete(report);
    }

}
