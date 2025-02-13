package com.si9nal.parker.global.common.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO에서 사용되는 데이터를 일정한 형식으로 변환하는 유틸리티 클래스
 * - 거리 값을 "X.Xkm" 형식으로 변환
 * - 시간 범위를 "HH:mm ~ HH:mm" 형식으로 변환
 * - null 또는 빈 값에 대한 기본 문자열 처리
 */
public class DtoFormatUtil {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 시간 범위를 "HH:mm ~ HH:mm" 형식으로 변환
     * @param startTime
     * @param endTime
     * @return
     */
    public static String formatTimeRange(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            return "정보 없음";
        }
        return String.format("%s ~ %s", startTime.format(TIME_FORMATTER), endTime.format(TIME_FORMATTER));
    }

    /**
     * 거리 값을 "X.Xkm" 형식으로 변환
     * @param distance
     * @return
     */
    public static String formatDistance(Double distance) {
        if (distance == null) {
            return "정보 없음";
        }
        return String.format("%.1fkm", distance);
    }

    /**
     * null 또는 빈 값에 대한 기본 문자열 처리
     * @param value
     * @return
     */
    public static String getValueOrDefault(Object value) {
        return (value != null && !value.toString().isEmpty()) ? value.toString() : "정보 없음";
    }
}
