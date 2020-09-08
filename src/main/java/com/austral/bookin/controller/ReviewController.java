package com.austral.bookin.controller;

import com.austral.bookin.dto.review.CreateReviewDTO;
import com.austral.bookin.dto.review.ReviewDTO;
import com.austral.bookin.dto.review.UpdateReviewDTO;
import com.austral.bookin.entity.Review;
import com.austral.bookin.service.review.ReviewService;
import com.austral.bookin.specification.ReviewSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> find(ReviewSpecification specification) {
        final List<Review> reviews = reviewService.find(specification);
        return ResponseEntity.ok(objectMapper.map(reviews, ReviewDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReviewDTO> find(@PathVariable Long id) {
        final Review review = reviewService.find(id);
        return ResponseEntity.ok(objectMapper.map(review, ReviewDTO.class));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody @Valid CreateReviewDTO createReviewDTO) {
        final Review review = reviewService.save(objectMapper.map(createReviewDTO, Review.class));
        return ResponseEntity.ok(objectMapper.map(review, ReviewDTO.class));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable Long id,
                                            @RequestPart("review") @Valid UpdateReviewDTO updateReviewDTO) {
        final Review review = reviewService.update(id, objectMapper.map(updateReviewDTO, Review.class));
        return ResponseEntity.ok(objectMapper.map(review, ReviewDTO.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReviewDTO> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}