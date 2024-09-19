package de.supercode.superBnB.dtos;

public record AddressSaveDto(
        String street,
        String houseNumber,
        String zipCode,
        String city
) {
}
