package de.supercode.superBnB.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingRequestDto(
        long propertyId,
        int numAdults,
        int numChildren,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {
}
