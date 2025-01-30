package com.si9nal.parker.parkingvioation.service;

import com.si9nal.parker.parkingvioation.dto.res.ParkingViolationDetailResponseDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingViolationDetailService {

    private final String filePath = "csv/parkingviolation/전국주정차금지_지정_구역표준데이터_UTF8.csv";

    public List<ParkingViolationDetailResponseDto> getAllParkingViolationDetails() {
        List<ParkingViolationDetailResponseDto> parkingViolationList = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(filePath);
            try (InputStream inputStream = resource.getInputStream();
                 Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreEmptyLines()
                        .parse(in);

                for (CSVRecord record : records) {
                    String sidoName = cleanHeader(record.get(0));  // 시도명
                    String sigunguName = cleanHeader(record.get(1));  // 시군구명
                    String roadName = cleanHeader(record.get(3));  // 도로명
                    String detailedLocation = cleanHeader(record.get(4));  // 상세위치
                    String managementPhoneNumber = cleanHeader(record.get(24));  // 관리기관전화번호

                    String weekdayTime = formatTimeRange(cleanHeader(record.get(8)), cleanHeader(record.get(9)));  // 평일 단속 시간
                    String saturdayTime = formatTimeRange(cleanHeader(record.get(12)), cleanHeader(record.get(13)));  // 토요일 단속 시간
                    String holidayTime = formatTimeRange(cleanHeader(record.get(16)), cleanHeader(record.get(17)));  // 공휴일 단속 시간

                    ParkingViolationDetailResponseDto parkingViolation = ParkingViolationDetailResponseDto.builder()
                            .sidoName(sidoName)
                            .sigunguName(sigunguName)
                            .roadName(roadName)
                            .detailedLocation(detailedLocation)
                            .managementPhoneNumber(managementPhoneNumber)
                            .weekdayTime(weekdayTime)
                            .saturdayTime(saturdayTime)
                            .holidayTime(holidayTime)
                            .build();

                    parkingViolationList.add(parkingViolation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("CSV 데이터 로드 중 오류 발생!");
        }

        return parkingViolationList;
    }

    private String formatTimeRange(String startTime, String endTime) {
        if (startTime == null || startTime.isBlank() || endTime == null || endTime.isBlank()) {
            return "단속 없음";
        }
        try {
            LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm"));
            return start.format(DateTimeFormatter.ofPattern("HH:mm")) + " ~ " + end.format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            return "단속 없음";
        }
    }

    private String cleanHeader(String value) {
        if (value == null) return null;
        return value.replace("\uFEFF", "").trim();
    }
}
