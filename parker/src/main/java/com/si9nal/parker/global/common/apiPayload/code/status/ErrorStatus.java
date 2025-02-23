package com.si9nal.parker.global.common.apiPayload.code.status;

import com.si9nal.parker.global.common.apiPayload.code.BaseErrorCode;
import com.si9nal.parker.global.common.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 멤버 관려 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "사용자가 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER4002", "이미 존재하는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER4003", "비밀번호가 틀립니다."),
    USER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "USER4004", "인증된 사용자가 존재하지 않습니다."),

    // 주차 공간 관련 에러 추가
    PARKING_SPACE_INVALID_LATITUDE(HttpStatus.BAD_REQUEST, "PARKING4001", "유효하지 않은 위도 값입니다. -90에서 90 사이의 값이어야 합니다."),
    PARKING_SPACE_INVALID_LONGITUDE(HttpStatus.BAD_REQUEST, "PARKING4002", "유효하지 않은 경도 값입니다. -180에서 180 사이의 값이어야 합니다."),
    PARKING_SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "PARKING4004", "주변에 주차 공간을 찾을 수 없습니다."),

    // 파일 관련 에러 추가
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_001", "파일 업로드에 실패하였습니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_002", "파일 삭제에 실패하였습니다."),

    // 신고 관련 에러
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4001", "해당 신고를 찾을 수 없습니다."),
    REPORT_UNAUTHORIZED(HttpStatus.FORBIDDEN, "REPORT4002", "해당 신고에 대한 권한이 없습니다."),
    REPORT_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "REPORT4003", "이미 처리된 신고입니다."),
    REPORT_INVALID_STATUS(HttpStatus.BAD_REQUEST, "REPORT4004", "유효하지 않은 신고 상태입니다."),

    // 불법주정차 관련 에러
    PARKING_VIOLATION_NOT_FOUND(HttpStatus.NOT_FOUND, "VIOLATION4001", "해당 위치의 불법 주정차 정보가 없습니다."),

    //단속 카메라 관련 에러
    CAMERA_LOCATION_INVALID_LATITUDE(HttpStatus.BAD_REQUEST, "CAMERA4001", "유효하지 않은 위도 값입니다. -90에서 90 사이의 값이어야 합니다."),
    CAMERA_LOCATION_INVALID_LONGITUDE(HttpStatus.BAD_REQUEST, "CAMERA4002", "유효하지 않은 경도 값입니다. -180에서 180 사이의 값이어야 합니다."),
    CAMERA_LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "CAMERA4003", "주변에 단속카메라를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
