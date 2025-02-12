package com.si9nal.parker.bookmark.repository;

import com.si9nal.parker.bookmark.domain.ViolationBookmark;
import com.si9nal.parker.parkingviolation.domain.ParkingViolation;
import com.si9nal.parker.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViolationBookmarkRepository extends JpaRepository<ViolationBookmark, Long> {
    Optional<ViolationBookmark> findByUserAndParkingViolation(User user, ParkingViolation parkingViolation);
}
