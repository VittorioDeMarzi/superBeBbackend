package de.supercode.superBnB.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SeasonalPriceResponseDto(
        long id,
        long propertyId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal pricePerNight
) {
}
