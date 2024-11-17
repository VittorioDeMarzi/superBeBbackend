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
        String zipCode,
        @NotBlank(message = "City cannot be empty")
        String city,
        @NotBlank(message = "Country cannot be empty")
        String country

) {
}
