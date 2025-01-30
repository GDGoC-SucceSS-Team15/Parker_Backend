package com.si9nal.parker.parkingvioation.service;

import com.si9nal.parker.parkingvioation.domain.ParkingViolation;
import com.si9nal.parker.parkingvioation.dto.res.ParkingViolationDetailResponseDto;
import com.si9nal.parker.parkingvioation.repository.ParkingViolationRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingViolationDetailService {

    private final ParkingViolationRepository parkingViolationRepository;
    private final String filePath = "csv/parkingviolation/전국주정차금지_지정_구역표준데이터_UTF8.csv";

    public ParkingViolationDetailService(ParkingViolationRepository parkingViolationRepository) {
        this.parkingViolationRepository = parkingViolationRepository;
    }

    @Transactional
    public void loadAndSaveParkingViolationDetail() {
        try {
            Resource resource = new ClassPathResource(filePath);
            try (InputStream inputStream = resource.getInputStream();
                 Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreEmptyLines()
                        .parse(in);

                List<ParkingViolation> parkingViolations = new ArrayList<>();

                for (CSVRecord record : records) {
                    String sidoName = cleanHeader(record.get(0));  // 시도명
                    String sigunguName = cleanHeader(record.get(1));  // 시군구명
                    String roadName = cleanHeader(record.get(3));  // 도로명
                    String detailedLocation = cleanHeader(record.get(4));  // 상세위치
                    String managementPhoneNumber = cleanHeader(record.get(24));  // 관리기관전화번호

                    LocalTime weekdayStartTime = parseTime(cleanHeader(record.get(8)));  // 평일 시작 시간
                    LocalTime weekdayEndTime = parseTime(cleanHeader(record.get(9)));  // 평일 종료 시간
                    LocalTime saturdayStartTime = parseTime(cleanHeader(record.get(12)));  // 토요일 시작 시간
                    LocalTime saturdayEndTime = parseTime(cleanHeader(record.get(13)));  // 토요일 종료 시간
                    LocalTime holidayStartTime = parseTime(cleanHeader(record.get(16)));  // 공휴일 시작 시간
                    LocalTime holidayEndTime = parseTime(cleanHeader(record.get(17)));  // 공휴일 종료 시간

                    ParkingViolation parkingViolation = ParkingViolation.builder()
                            .sidoName(sidoName)
                            .sigunguName(sigunguName)
                            .roadName(roadName)
                            .detailedLocation(detailedLocation)
                            .managementPhoneNumber(managementPhoneNumber)
                            .weekdayStartTime(weekdayStartTime)
                            .weekdayEndTime(weekdayEndTime)
                            .saturdayStartTime(saturdayStartTime)
                            .saturdayEndTime(saturdayEndTime)
                            .holidayStartTime(holidayStartTime)
                            .holidayEndTime(holidayEndTime)
                            .build();

                    parkingViolations.add(parkingViolation);
                }

                parkingViolationRepository.saveAll(parkingViolations);
                System.out.println("CSV 데이터가 성공적으로 저장되었습니다!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("CSV 데이터 로드 중 오류 발생!");
        }
    }

    private LocalTime parseTime(String timeString) {
        if (timeString == null || timeString.isBlank()) {
            return null;
        }
        try {
            return LocalTime.parse(timeString);
        } catch (Exception e) {
            System.err.println("LocalTime 변환 오류: " + timeString);
            return null;
        }
    }

    private String cleanHeader(String value) {
        if (value == null) return null;
        return value.replace("\uFEFF", "").trim();
    }

    public List<ParkingViolationDetailResponseDto> getAllParkingViolationDetails() {
        List<ParkingViolation> parkingViolations = parkingViolationRepository.findAll();

        return parkingViolations.stream()
                .map(ParkingViolationDetailResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
