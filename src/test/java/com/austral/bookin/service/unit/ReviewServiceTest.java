package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.ReviewRepository;
import com.austral.bookin.service.review.ReviewService;
import com.austral.bookin.specification.ReviewSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceTest {
    @Mock
    private ReviewSpecification reviewSpecification;

    @MockBean
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    @Test
    public void contextLoads() {
        assertNotNull(reviewService);
    }

    @Test
    @DisplayName("Given reviews list, when find all, then return reviews")
    public void givenReviews_WhenFindAll_ReturnReviews() {
        doReturn(Arrays.asList(new Review(), new Review()))
                .when(reviewRepository)
                .findAll(reviewSpecification);

        final List<Review> reviews = reviewService.find(reviewSpecification);

        verify(reviewRepository).findAll(reviewSpecification);
        assertEquals(2, reviews.size());
    }

    @Test
    @DisplayName("Given empty list, when find all, then return empty list")
    public void givenEmptyList_whenFindAll_ThenReturnEmptyList() {
        doReturn(Collections.emptyList())
                .when(reviewRepository)
                .findAll(reviewSpecification);

        final List<Review> reviews = reviewService.find(reviewSpecification);

        assertTrue(reviews.isEmpty());
        verify(reviewRepository).findAll(reviewSpecification);
    }

    @Test
    @DisplayName("Given present optional review, when find by id, then return review")
    public void givenPresentOptionalReview_whenFindById_ThenReturnReview() {
        doReturn(Optional.of(new Review()))
                .when(reviewRepository)
                .findById(4L);

        final Review review = reviewService.find(4L);

        assertNotNull(review);
        verify(reviewRepository).findById(4L);
    }

    @Test
    @DisplayName("Given empty optional review, when find by id, then throw not found exception")
    public void givenEmptyOptionalReview_whenFindById_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(reviewRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> reviewService.find(4L));
        verify(reviewRepository).findById(4L);
    }

    @Test
    @DisplayName("Given review, when save, then return review")
    public void givenReview_whenSave_thenReturnReview() {
        doReturn(new Review())
                .when(reviewRepository)
                .save(any(Review.class));

        final Review review = reviewService.save(new Review());

        assertNotNull(review);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    @DisplayName("Given present optional review, when update, then update review")
    public void givenPresentOptionalReview_whenUpdate_thenUpdateReview() {
        doReturn(Optional.of(new Review()))
                .when(reviewRepository)
                .findById(4L);

        doReturn(new Review())
                .when(reviewRepository)
                .save(any(Review.class));

        final Review review = reviewService.update(4L, new Review());

        assertNotNull(review);
        verify(reviewRepository).findById(4L);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    @DisplayName("Given present optional review, when update, then throw not found exception")
    public void givenEmptyOptionalReview_whenUpdate_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(reviewRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> reviewService.update(4L, new Review()));
        verify(reviewRepository).findById(4L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    @DisplayName("Given present optional review, when delete, then delete review")
    public void givenPresentOptionalReview_whenDelete_thenDeleteReview() {
        doReturn(Optional.of(new Review()))
                .when(reviewRepository)
                .findById(4L);

        reviewService.delete(4L);

        verify(reviewRepository).delete(any(Review.class));
    }

    @Test
    @DisplayName("Given empty optional review, when delete, then throw not found exception")
    public void givenEmptyOptionalReview_whenDelete_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(reviewRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> reviewService.delete(4L));
        verify(reviewRepository).findById(4L);
        verify(reviewRepository, never()).delete(any(Review.class));
    }
}