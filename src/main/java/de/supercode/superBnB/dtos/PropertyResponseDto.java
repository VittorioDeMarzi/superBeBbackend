package de.supercode.superBnB.dtos;

import java.math.BigDecimal;

public record PropertyResponseDto(
        long id,
        String title,
        String description,
        String street,
        String houseNumber,
        String zipCode,
        String city,
        int maxNumGuests,
        BigDecimal pricePerNight
) {
}
