package com.si9nal.parker.parkingvioation.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ParkingViolationDataInitializer implements CommandLineRunner {

    private final ParkingViolationDetailService parkingViolationDetailService;

    public ParkingViolationDataInitializer(ParkingViolationDetailService parkingViolationDetailService) {
        this.parkingViolationDetailService = parkingViolationDetailService;
    }

    @Override
    public void run(String... args) {
        try {
            parkingViolationDetailService.loadAndSaveParkingViolationDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
