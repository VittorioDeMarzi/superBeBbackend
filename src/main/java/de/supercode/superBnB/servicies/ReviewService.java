package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.ReviewRequestDto;
import de.supercode.superBnB.dtos.ReviewResponseDto;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.entities.property.Review;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.mappers.ReviewDtoMapper;
import de.supercode.superBnB.repositories.ReviewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private PropertyService propertyService;
    private UserService userService;

    public ReviewService(ReviewRepository reviewRepository, PropertyService propertyService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.propertyService = propertyService;
        this.userService = userService;
    }

    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto, Authentication auth) {
        if (!auth.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }
        User user = userService.findUserByEmail(auth.getName());
        Property property = propertyService.findPropertyById(reviewRequestDto.propertyId());

        Review review = new Review();
        review.setStars(reviewRequestDto.stars());
        review.setContent(reviewRequestDto.content());
        review.setProperty(property);
        review.setUser(user);

        return ReviewDtoMapper.mapToDto(reviewRepository.save(review));
    }

    public List<ReviewResponseDto> getAllReviewsByProperty(Long propertyId) {
        Property property = propertyService.findPropertyById(propertyId);
        List<Review> reviews = property.getReviews();
        return reviews.stream().map(ReviewDtoMapper::mapToDto).toList();
    }

    public List<ReviewResponseDto> getAllReviewsByUser(Long userId) {
        User user = userService.findUserById(userId);
        List<Review> reviews = reviewRepository.findByUser(user);
        return reviews.stream().map(ReviewDtoMapper::mapToDto).toList();
    }
}
