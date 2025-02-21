package de.supercode.superBnB.dtos;

import java.time.LocalDate;

public record UserProfileDto(
        long userId,
        String email,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber
) {
}
