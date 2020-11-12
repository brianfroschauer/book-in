package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.UserReviewController;
import com.austral.bookin.dto.review.ReviewWithBookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.review.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private UserReviewController userReviewController;

    @Test
    public void contextLoads() {
        assertNotNull(userReviewController);
    }

    @Test
    @DisplayName("Given user id, then return OK response")
    public void givenUserId_thenReturnOkResponse() {
        Review review = new Review(4, "Muy bueno", new User(), new Book());
        Review review1 = new Review(2, "Muy malo", new User(), new Book());

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review1);

        Mockito.doReturn(reviews)
                .when(reviewService)
                .findByUser(1L);

        final ResponseEntity<List<ReviewWithBookDTO>> responseEntity = userReviewController.findByUser(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(reviewService, times(1)).findByUser(1L);
    }
}
