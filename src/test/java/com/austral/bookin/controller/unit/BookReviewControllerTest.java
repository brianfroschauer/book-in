package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.BookReviewController;
import com.austral.bookin.dto.review.ReviewWithUserDTO;
import com.austral.bookin.entity.Author;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BookReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private BookReviewController bookReviewController;

    @Test
    public void contextLoads() {
        assertNotNull(bookReviewController);
    }

    @Test
    @DisplayName("Given search author spec, then return OK response")
    public void givenSearchAuthorSpec_thenReturnOkResponse() {
        Author author = new Author("firstName", "lastName", "US", new Date());
        Author author2 = new Author("firstName2", "lastName2", "GB", new Date());

        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        Mockito.doReturn(authors)
                .when(reviewService)
                .findByBook(1L);

        final ResponseEntity<List<ReviewWithUserDTO>> responseEntity = bookReviewController.findByBook(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(reviewService, times(1)).findByBook(1L);
    }
}
