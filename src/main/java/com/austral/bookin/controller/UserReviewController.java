package com.austral.bookin.controller;

import com.austral.bookin.dto.review.ReviewDTO;
import com.austral.bookin.entity.Review;
import com.austral.bookin.service.review.ReviewService;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserReviewController {

    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    public UserReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping("/users/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findByUser(@PathVariable Long id) {
        List<Review> reviews = reviewService.findByUser(id);
        return ResponseEntity.ok(objectMapper.map(reviews, ReviewDTO.class));
    }
}