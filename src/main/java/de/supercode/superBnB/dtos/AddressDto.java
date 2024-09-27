package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        @NotBlank
        String street,
        @NotBlank
        String houseNumber,
        @NotBlank
        String zipCode,
        @NotBlank
        String city,
        @NotBlank
        String country
) {
}
