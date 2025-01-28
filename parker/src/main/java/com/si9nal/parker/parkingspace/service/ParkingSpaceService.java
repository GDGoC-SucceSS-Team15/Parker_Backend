package com.si9nal.parker.parkingspace.service;

import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.parkingspace.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ParkingSpaceService {

    private final ParkingSpaceRepository repository;

    public ParkingSpaceService(ParkingSpaceRepository repository) {
        this.repository = repository;
    }

    public List<ParkingSpace> getParkingSpaces(Double latitude, Double longitude, Double radius) throws Exception {
        String pointWkt = String.format("POINT(%f %f)", longitude, latitude);
        return repository.findWithinRadius(pointWkt, radius);
    }
}
