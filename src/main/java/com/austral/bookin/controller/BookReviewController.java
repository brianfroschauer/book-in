package com.austral.bookin.controller;

import com.austral.bookin.dto.review.ShowReviewDTO;
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
public class BookReviewController {

    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    public BookReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping("/books/{id}/reviews")
    public ResponseEntity<List<ShowReviewDTO>> findByBook(@PathVariable Long id) {
        List<Review> reviews = reviewService.findByBook(id);
        return ResponseEntity.ok(objectMapper.map(reviews, ShowReviewDTO.class));
    }
}