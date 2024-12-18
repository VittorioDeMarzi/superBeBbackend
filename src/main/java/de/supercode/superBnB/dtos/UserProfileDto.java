package de.supercode.superBnB.dtos;

import java.time.LocalDate;

public record UserProfileDto(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber
) {
}
