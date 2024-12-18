package de.supercode.superBnB.dtos;

import java.math.BigDecimal;

public record RequestPriceAndAvailabilityResponseDto(
        Boolean isAvailable,
        BigDecimal totalPrice,
        BigDecimal pricePerNight,
        long numNights) {

}
