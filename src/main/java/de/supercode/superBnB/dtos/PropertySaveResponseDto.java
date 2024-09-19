package de.supercode.superBnB.dtos;

import java.math.BigDecimal;

public record PropertySaveResponseDto(
        long id,
        String street,
        String houseNumber,
        String zipCode,
        String city,
        int maxNumGuests,
        BigDecimal pricePerNight
) {
}
