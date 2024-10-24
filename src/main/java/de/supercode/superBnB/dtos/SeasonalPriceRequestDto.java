package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SeasonalPriceRequestDto(
        @Future
        LocalDate startDate,
        @Future
        LocalDate endDate,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
        BigDecimal pricePerNight
) {
}
