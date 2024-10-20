package de.supercode.superBnB.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserFirstRegistrationDto(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid username format")
        String username,
        @NotBlank(message = "Password cannot be empty")
        String password
) {
}