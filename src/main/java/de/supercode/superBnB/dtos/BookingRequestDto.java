package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record BookingRequestDto(
        Long propertyId,
        @Min(value = 1, message = "At least 1 adult hat to be present")
        int numAdults,
        @Min(0)
        int numChildren,
        @Future(message = "Date can not be in the past")
        LocalDate checkInDate,
        @Future(message = "Date can not be in the past")
        LocalDate checkOutDate
) {
}
