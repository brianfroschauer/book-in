package com.austral.bookin.controller.integration;

import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.review.CreateReviewDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.Review;
import com.austral.bookin.service.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private VelocityEngine velocityEngine;

    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @Test
    @DisplayName("Given valid review, when create, then return Ok response")
    public void givenValidReview_whenCreate_thenReturnOkResponse() throws Exception {
        final CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setStars(2);
        createReviewDTO.setComment("Very good");
        createReviewDTO.setBook(new BookDTO());
        createReviewDTO.setUser(new UserDTO());

        Mockito.doReturn(new Review())
                .when(reviewService)
                .save(any(Review.class));

        mockMvc.perform(post("/reviews")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createReviewDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given too few stars, when create, then return Bad Request response")
    public void givenTooFewStars_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setStars(0);
        createReviewDTO.setComment("Very good");
        createReviewDTO.setBook(new BookDTO());
        createReviewDTO.setUser(new UserDTO());

        mockMvc
                .perform(post("/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReviewDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given too many stars, when create, then return Bad Request response")
    public void givenTooManyStars_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setStars(7);
        createReviewDTO.setComment("Very good");
        createReviewDTO.setBook(new BookDTO());
        createReviewDTO.setUser(new UserDTO());

        mockMvc
                .perform(post("/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReviewDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given long comment, when create, then return Bad Request response")
    public void givenLongComment_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setStars(2);
        createReviewDTO.setComment("Loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong");
        createReviewDTO.setBook(new BookDTO());
        createReviewDTO.setUser(new UserDTO());

        mockMvc
                .perform(post("/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReviewDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given invalid book, when create, then return Bad Request response")
    public void givenInvalidBook_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setStars(2);
        createReviewDTO.setComment("Very good");
        createReviewDTO.setBook(null);
        createReviewDTO.setUser(new UserDTO());

        mockMvc
                .perform(post("/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReviewDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given invalid user, when create, then return Bad Request response")
    public void givenInvalidUser_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        createReviewDTO.setStars(2);
        createReviewDTO.setComment("Very good");
        createReviewDTO.setBook(new BookDTO());
        createReviewDTO.setUser(null);

        mockMvc
                .perform(post("/reviews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createReviewDTO)))
                .andExpect(status().isBadRequest());
    }
}
