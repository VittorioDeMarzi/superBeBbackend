package de.supercode.superBnB.dtos;

public record AddressDto(
        String street,
        String houseNumber,
        String zipCode,
        String city,
        String country
) {
}
