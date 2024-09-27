package de.supercode.superBnB.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SeasonalPriceDto(
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal pricePerNight
) {
}
