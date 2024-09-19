package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
