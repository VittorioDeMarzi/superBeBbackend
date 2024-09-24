package de.supercode.superBnB.dtos;

import java.time.LocalDate;

public record UserResponseDto(
    long userId,
    String username,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String phoneNumber,
    String street,
    String houseNumber,
    String zipCode,
    String city,
    String country
){
}
