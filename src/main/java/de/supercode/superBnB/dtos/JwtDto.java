package de.supercode.superBnB.dtos;

public record JwtDto(
        String token,
        String email,
        String role
) {
}
