package de.supercode.superBnB.controllers;


import de.supercode.superBnB.dtos.ReviewRequestDto;
import de.supercode.superBnB.dtos.ReviewResponseDto;
import de.supercode.superBnB.servicies.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // USER - write new review
    @PostMapping
    public ResponseEntity<ReviewResponseDto> writeNewReview(Authentication authentication, @RequestBody ReviewRequestDto reviewRequestDto) {
        return ResponseEntity.ok(reviewService.createReview(reviewRequestDto, authentication));
    }

    // ALL - get all review for one property
    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAllReviewsForProperty(@RequestParam Long propertyId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByProperty(propertyId));
    }

    // ADMIN - get all reviews from one User
    @GetMapping("/property")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviewsForUser(@RequestParam Long userId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByUser(userId));
    }
}
