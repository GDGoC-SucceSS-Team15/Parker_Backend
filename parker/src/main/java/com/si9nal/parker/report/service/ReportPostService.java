package com.si9nal.parker.report.service;

import com.si9nal.parker.global.common.apiPayload.code.status.ErrorStatus;
import com.si9nal.parker.global.common.apiPayload.exception.GeneralException;
import com.si9nal.parker.global.common.s3.AmazonS3Manager;
import com.si9nal.parker.global.common.s3.Uuid;
import com.si9nal.parker.global.common.s3.UuidRepository;
import com.si9nal.parker.report.domain.Report;
import com.si9nal.parker.report.domain.enums.ApprovalStatus;

import com.si9nal.parker.report.dto.req.ReportPostRequestDto;
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

import java.util.UUID;

@Service
@Transactional
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


    public ReportPostResponseDto createReport(String email, ReportPostRequestDto requestDto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // Create report
        Report report = requestDto.toEntity();
        report.setUser(user);

        MultipartFile image = requestDto.getImage();
        if (image != null && !image.isEmpty()) {
            Uuid imgUuid = uuidRepository.save(Uuid.builder().uuid(UUID.randomUUID().toString()).build());
            String imgURL = s3Manager.uploadFile(s3Manager.generateReportKeyName(imgUuid), requestDto.getImage());
            report.setImageUrl(imgURL);
        }

        // Save and return
        Report savedReport = reportRepository.save(report);
        return ReportPostResponseDto.fromEntity(savedReport);
    }
}
