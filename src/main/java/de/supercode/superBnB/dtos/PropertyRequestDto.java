package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PropertyRequestDto(
        @NotBlank(message = "Title cannot be empty")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,
        @NotBlank(message = "Description cannot be empty")
        @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
        String description,
        @NotBlank(message = "Street cannot be empty")
        String street,
        @NotBlank(message = "House number cannot be empty")
        @Size(max = 10, message = "House number cannot exceed 10 characters")
        String houseNumber,
        @NotBlank(message = "ZIP code cannot be empty")
        @Pattern(regexp = "\\d{5}", message = "ZIP code must be a 5-digit number")
        String zipCode,
        @NotBlank(message = "City cannot be empty")
        String city,
        @NotBlank(message = "Country cannot be empty")
        String country,
        @Min(value = 1, message = "Number of rooms must be at least 1")
        int rooms,
        @Min(value = 1, message = "Number of guests must be at least 1")
        int maxNumGuests,
        @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
        BigDecimal minPricePerNight
){}
