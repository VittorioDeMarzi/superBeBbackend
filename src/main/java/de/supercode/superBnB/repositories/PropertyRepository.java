package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.property.Property;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    List<Property> findByIsPublic(boolean isPublic, Pageable pageable);
}
