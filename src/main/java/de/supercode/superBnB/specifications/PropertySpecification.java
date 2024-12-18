package de.supercode.superBnB.specifications;

import de.supercode.superBnB.entities.property.Property;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PropertySpecification {

    public static Specification<Property> isPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null || maxPrice == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.between(root.get("minPricePerNight"), minPrice, maxPrice);
        };
    }

    public static Specification<Property> numGuests(int maxNumGuests) {
        return (root, query, criteriaBuilder) -> {
            if (maxNumGuests == 0) return criteriaBuilder.conjunction();
            return criteriaBuilder.greaterThanOrEqualTo(root.get("maxNumGuests"), maxNumGuests);
        };
    }

    public static Specification<Property> hasCity (String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null || city.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(criteriaBuilder.lower(root.join("address").get("city")), city.toLowerCase());
        };
    }

    public static Specification<Property> hasMinRooms (int rooms) {
        return (root, query, criteriaBuilder) -> {
            if (rooms == 0) return criteriaBuilder.conjunction();
            return criteriaBuilder.greaterThanOrEqualTo(root.get("rooms"), rooms);
        };
    }

    public static Specification<Property> isPublic (boolean isPublic) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("isPublic"), isPublic);
        };
    }


}
