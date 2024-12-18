package de.supercode.superBnB.dtos;

import de.supercode.superBnB.entities.user.Role;

import java.time.LocalDate;

public record UserShortDto(
        long userId,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber
) {
}
