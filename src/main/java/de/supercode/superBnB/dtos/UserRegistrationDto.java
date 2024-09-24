package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRegistrationDto(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid username format")
        String username,
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
        String password,
        @Pattern(regexp = "^(ADMIN|USER)$", message = "Role not valid: 'ADMIN' or 'USER'")
        String role,
        @NotBlank(message = "Name cannot be empty")
        String firstName,
        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        LocalDate dateOfBirth,
        String phoneNumber,
        String street,
        String houseNumber,
        String zipCode,
        String city,
        String country
) {
}
