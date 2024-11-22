package de.supercode.superBnB.dtos;

public record ReviewResponseDto(
        Long id,
        Long userId,
        String username,
        Long propertyId,
        int stars,
        String content
) {
}
