package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.property.Review;
import de.supercode.superBnB.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
}
