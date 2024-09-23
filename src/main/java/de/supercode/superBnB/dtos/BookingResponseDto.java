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
        long totalNights,
        BigDecimal totalPrice,
        PropertyResponseDto property
) {
}
