package com.si9nal.parker.bookmark.repository;

import com.si9nal.parker.bookmark.domain.SpaceBookmark;
import com.si9nal.parker.parkingspace.domain.ParkingSpace;
import com.si9nal.parker.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceBookmarkRepository extends JpaRepository<SpaceBookmark, Long> {

    boolean existsByUserAndParkingSpace(User user, ParkingSpace parkingSpace);

    Optional<SpaceBookmark> findByUserAndParkingSpace(User user, ParkingSpace parkingSpace);
}
