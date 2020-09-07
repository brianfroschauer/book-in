package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.ReviewController;
import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.book.UpdateBookDTO;
import com.austral.bookin.dto.review.ReviewDTO;
import com.austral.bookin.dto.review.UpdateReviewDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.service.review.ReviewService;
import com.austral.bookin.specification.ReviewSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewControllerTest {

    @Mock
    private ReviewSpecification reviewSpecification;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ReviewController reviewController;

    @Test
    public void contextLoads() {
        assertNotNull(reviewController);
    }

    @Test
    @DisplayName("When find all, then return Ok response")
    public void whenFindAll_thenReturnOkResponse() {
        doReturn(Collections.emptyList())
                .when(reviewService)
                .find(reviewSpecification);

        final ResponseEntity<List<ReviewDTO>> responseEntity = reviewController.find(reviewSpecification);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
        verify(reviewService, times(1)).find(reviewSpecification);
    }

    @Test
    @DisplayName("Given existing review id, when find by id, then return Ok response")
    public void givenExistingReviewId_whenFindById_thenReturnOkResponse() {
        Mockito.doReturn(new Review())
                .when(reviewService)
                .find(1L);

        final ResponseEntity<ReviewDTO> responseEntity = reviewController.find(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(reviewService, times(1)).find(1L);
    }

    @Test
    @DisplayName("Given non existent review id, when find by ID, then throw Not Found exception")
    public void givenNonExistentReviewId_whenFindById_thenReturnNotFoundResponse() {
        Mockito.doThrow(NotFoundException.class)
                .when(reviewService)
                .find(1L);

        assertThrows(NotFoundException.class, () -> reviewController.find(1L));
    }

    @Test
    @DisplayName("Given existing review, when update, then return Ok response")
    public void givenExistingReview_whenUpdate_thenReturnOkResponse() {
        final UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO();
        updateReviewDTO.setStars(2);
        updateReviewDTO.setComment("Very interesting");

        doReturn(new Review())
                .when(reviewService)
                .update(eq(1L), any(Review.class));

        final ResponseEntity<ReviewDTO> responseEntity = reviewController.update(1L, updateReviewDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(reviewService, times(1)).update(eq(1L), any(Review.class));
    }

    @Test
    @DisplayName("Given non existent review, when update, then throw Not Found exception")
    public void givenNonExistentReview_whenUpdate_thenReturnThrowNotFoundException() {
        final UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO();
        updateReviewDTO.setStars(2);
        updateReviewDTO.setComment("Very interesting");

        doThrow(NotFoundException.class)
                .when(reviewService)
                .update(eq(1L), any(Review.class));

        assertThrows(NotFoundException.class, () -> reviewController.update(1L, updateReviewDTO));
        verify(reviewService, times(1)).update(eq(1L), any(Review.class));
    }

    @Test
    @DisplayName("Given existing review, when delete, then return No Content response")
    public void givenExistingReview_whenDelete_thenReturnNoContentResponse() {
        doReturn(new Review())
                .when(reviewService)
                .find(1L);

        final ResponseEntity<ReviewDTO> responseEntity = reviewController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(reviewService, times(1)).delete(1L);
    }
}