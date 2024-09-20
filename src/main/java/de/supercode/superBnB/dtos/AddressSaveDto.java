package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressSaveDto(
        @NotBlank(message = "Street cannot be empty")
        String street,
        @NotBlank(message = "House number cannot be empty")
        @Size(max = 10, message = "House number too long")
        String houseNumber,
        @NotBlank(message = "ZIP code cannot be empty")
        @Pattern(regexp = "\\d{5}", message = "ZIP code must be a 5-digit number")
        String zipCode,
        @NotBlank(message = "City cannot be empty")
        String city
) {
}
