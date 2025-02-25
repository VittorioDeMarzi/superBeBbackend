package de.supercode.superBnB.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record PropertyResponseDto(
        long id,
        String title,
        String description,
        String street,
        String houseNumber,
        String zipCode,
        String city,
        String country,
        int rooms,
        int maxNumGuests,
        BigDecimal minPricePerNight,
        List<String> picUrls,
        Boolean isPublic
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
