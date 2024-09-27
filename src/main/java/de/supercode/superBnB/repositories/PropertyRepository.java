package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
