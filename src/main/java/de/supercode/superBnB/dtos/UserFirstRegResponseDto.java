package de.supercode.superBnB.dtos;

public record UserFirstRegResponseDto(
        long userId,
        String username,
        String role
) {
}
