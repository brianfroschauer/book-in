package com.austral.bookin.controller.integration;

import com.austral.bookin.dto.book.CreateBookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.book.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @Test
    @DisplayName("Given valid book, when create, then return Ok response")
    public void givenValidBook_whenCreate_thenReturnOkResponse() throws Exception {
        final CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setTitle("Oblivion");
        createBookDTO.setGenre("Fantasy");
        createBookDTO.setLanguage("English");
        createBookDTO.setDate(new Date(115, Calendar.AUGUST, 12));

        Mockito.doReturn(new Book())
                .when(bookService)
                .save(any(Book.class), any());

        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", objectMapper.writeValueAsString(createBookDTO).getBytes());
        MockMultipartFile photo = new MockMultipartFile("photo", "", "multipart/form-data", new byte[2]);

        mockMvc
                .perform(MockMvcRequestBuilders.multipart("/books")
                        .file(book)
                        .file(photo))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given book with short or empty title, when create, then return Bad Request response")
    public void givenBookWithShortTitle_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setTitle("O");
        createBookDTO.setGenre("Fantasy");
        createBookDTO.setLanguage("English");
        createBookDTO.setDate(new Date(115, Calendar.AUGUST, 12));

        Mockito.doReturn(new Book())
                .when(bookService)
                .save(any(Book.class), any());

        MockMultipartFile book = new MockMultipartFile("book", "", "application/json", objectMapper.writeValueAsString(createBookDTO).getBytes());
        MockMultipartFile photo = new MockMultipartFile("photo", "", "multipart/form-data", new byte[2]);

        mockMvc
                .perform(MockMvcRequestBuilders.multipart("/books")
                        .file(book)
                        .file(photo))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given book with very long title, when create, then return Bad Request response")
    public void givenBookWithVeryLongTitle_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setTitle("Oblivionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        createBookDTO.setGenre("Fantasy");
        createBookDTO.setLanguage("English");
        createBookDTO.setDate(new Date(115, Calendar.AUGUST, 12));

        mockMvc
                .perform(post("/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createBookDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given book with wrong publication date, when create, then return Bad Request response")
    public void givenBookWithWrongDate_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateBookDTO createBookDTO = new CreateBookDTO();
        createBookDTO.setTitle("Oblivion");
        createBookDTO.setGenre("Fantasy");
        createBookDTO.setLanguage("English");
        createBookDTO.setDate(new Date(125, Calendar.AUGUST, 12));

        mockMvc
                .perform(post("/books")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createBookDTO)))
                .andExpect(status().isBadRequest());
    }
}