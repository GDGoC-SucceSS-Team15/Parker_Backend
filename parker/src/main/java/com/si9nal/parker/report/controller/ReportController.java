package com.si9nal.parker.report.controller;


import com.si9nal.parker.global.common.apiPayload.ApiResponse;
import com.si9nal.parker.global.common.apiPayload.code.status.SuccessStatus;
import com.si9nal.parker.parkingspace.dto.res.ParkingSpaceMapDto;
import com.si9nal.parker.report.dto.req.ReportPostRequestDto;
import com.si9nal.parker.report.dto.res.ReportListResponseDto;
import com.si9nal.parker.report.dto.res.ReportPostResponseDto;
import com.si9nal.parker.report.service.ReportPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@RestController
@RequestMapping("/api/report")
@Tag(name = "불법주정차 구역 신고 API", description = "불법 주정차 단속 구역을 신고 기능을 제공하는 API")
public class ReportController {
    private final ReportPostService reportService;

    public ReportController(ReportPostService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "불법주정차 구역 신고 API",
            description = "불법주정차 단속 구역을 신고하는 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "불법주정차 구역 신고 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ParkingSpaceMapDto.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            )
    })
    public ResponseEntity<ApiResponse<ReportPostResponseDto>> postReport(
            @AuthenticationPrincipal String email,
            @ModelAttribute ReportPostRequestDto request) {

        ReportPostResponseDto response = reportService.createReport(email, request);

        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, response)
        );
    }

    @GetMapping("/my")
    @Operation(summary = "내 신고 목록 조회 API",
            description = "사용자가 신고한 불법주정차 목록을 조회하는 API입니다.")
    public ResponseEntity<ApiResponse<List<ReportListResponseDto>>> getMyReports(
            @AuthenticationPrincipal String email) {
        List<ReportListResponseDto> response = reportService.getMyReports(email);
        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, response)
        );
    }

    @DeleteMapping("/delete/{reportId}")
    @Operation(summary = "신고 철회 API",
            description = "사용자가 신고한 불법주정차 신고를 철회하는 API입니다.")
    public ResponseEntity<ApiResponse<Void>> withdrawReport(
            @AuthenticationPrincipal String email,
            @PathVariable Long reportId) {
        reportService.withdrawReport(email, reportId);
        return ResponseEntity.ok(
                ApiResponse.of(SuccessStatus._OK, null)
        );
    }
}