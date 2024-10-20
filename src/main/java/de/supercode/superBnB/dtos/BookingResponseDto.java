package de.supercode.superBnB.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponseDto(
        long id,
        long userId,
        String bookingCode,
        LocalDateTime bookingDate,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        int numAdults,
        int numChildren,
        int totGuests,
        long totalNights,
        BigDecimal totalPrice,
        PropertyResponseDto property
) {
}
