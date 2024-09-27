package de.supercode.superBnB.dtos;

import de.supercode.superBnB.entities.user.Role;

import java.time.LocalDate;

public record UserResponseDto(
    long userId,
    Role role,
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
