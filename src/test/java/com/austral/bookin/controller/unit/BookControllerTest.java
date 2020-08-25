package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.BookController;
import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.specification.BookSpecification;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class BookControllerTest {

    @Mock
    private BookSpecification bookSpecification;

    @MockBean
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @Test
    public void contextLoads() {
        assertNotNull(bookController);
    }

    @Test
    @DisplayName("When find all, then return Ok response")
    public void whenFindAll_thenReturnOkResponse() {
        doReturn(Collections.emptyList())
                .when(bookService)
                .find(bookSpecification);

        final ResponseEntity<List<BookDTO>> responseEntity = bookController.find(bookSpecification);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
        verify(bookService, times(1)).find(bookSpecification);
    }

    @Test
    @DisplayName("Given existing book id, when find by ID, then return Ok response")
    public void givenExistingBookId_whenFindById_thenReturnOkResponse() {
        Mockito.doReturn(new Book())
                .when(bookService)
                .find(1L);

        final ResponseEntity<BookDTO> responseEntity = bookController.find(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).find(1L);
    }

    @Test
    @DisplayName("Given non existent book id, when find by ID, then throw Not Found exception")
    public void givenNonExistentBookId_whenFindById_thenReturnNotFoundResponse() {
        Mockito.doThrow(NotFoundException.class)
                .when(bookService)
                .find(1L);

        assertThrows(NotFoundException.class, () -> bookController.find(1L));
    }
}