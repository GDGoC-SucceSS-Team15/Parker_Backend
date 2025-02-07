package com.si9nal.parker.report.dto.req;

import com.si9nal.parker.report.domain.Report;
import com.si9nal.parker.report.domain.enums.ApprovalStatus;
import com.si9nal.parker.user.domain.User;
import com.si9nal.parker.user.domain.enums.Status;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReportPostRequestDto {
    private String address;
    private MultipartFile image; // Optional image file

    // null 체크 메서드 추가
    public boolean hasImage() {
        return image != null && !image.isEmpty();
    }

    public Report toEntity() {
        return Report.builder()
                .address(this.address)
                .approvalStatus(ApprovalStatus.PENDING)
                .build();
    }
}
