package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.Booking;
import de.supercode.superBnB.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> findByUserId(long userId);
}
