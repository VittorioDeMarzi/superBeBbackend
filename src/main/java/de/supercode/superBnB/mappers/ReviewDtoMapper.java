package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.ReviewResponseDto;
import de.supercode.superBnB.entities.property.Review;

public class ReviewDtoMapper {
    public static ReviewResponseDto mapToDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getUsername(),
                review.getProperty().getId(),
                review.getStars(),
                review.getContent()
        );
    }
}
