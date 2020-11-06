package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.repository.ReviewRepository;
import com.austral.bookin.service.review.ReviewService;
import com.austral.bookin.specification.ReviewSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

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

    @MockBean
    private BookRepository bookRepository;

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
    @DisplayName("Given present optional review, when update, then update review")
    public void givenPresentOptionalReview_whenUpdate_thenUpdateReview() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        Review review1 = new Review(5, "hola", new User(), book);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);

        doReturn(Optional.of(review1))
                .when(reviewRepository)
                .findById(4L);

        doReturn(review1)
                .when(reviewRepository)
                .save(any(Review.class));

        doReturn(reviews)
                .when(reviewRepository)
                .findByBook(1L);

        doReturn(Optional.of(book))
                .when(bookRepository)
                .findById(1L);

        final Review review = reviewService.update(4L, review1);

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
