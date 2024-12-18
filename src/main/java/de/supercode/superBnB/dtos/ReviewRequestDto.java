package de.supercode.superBnB.dtos;

public record ReviewRequestDto(
        String content,
        Long propertyId,
        int stars
) {
}
