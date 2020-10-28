package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.BookController;
import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.book.UpdateBookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.repository.ReviewRepository;
import com.austral.bookin.service.book.BookService;
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
import java.util.Optional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private BookController bookController;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    public void contextLoads() {
        assertNotNull(bookController);
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
  
    @Test
    @DisplayName("Get list of books ordered by stars, then return Ok response")
    public void getOrderedList_thenReturnOkResponse() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        Book book2 = new Book(2L, "title2", "Aventura", "en", new Date(), new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book2);
        books.add(book);

        Mockito.doReturn(books)
                .when(bookRepository)
                .sortByStars(2);

        final ResponseEntity<List<BookDTO>> responseEntity = bookController.sortByStars(2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).sortByStars(2);
    }

    @Test
    @DisplayName("Get list of books of the given genre ordered by stars, then return Ok response")
    public void getOrderedListByGenre_thenReturnOkResponse() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        Book book2 = new Book(2L, "title2", "Aventura", "en", new Date(), new ArrayList<>());

        List<Book> books = new ArrayList<>();
        books.add(book2);
        books.add(book);

        Mockito.doReturn(books)
                .when(bookRepository)
                .sortByGenre("Aventura", 2);

        final ResponseEntity<List<BookDTO>> responseEntity = bookController.sortByGenre("Aventura", 2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).sortByGenre("Aventura", 2);
    }

    @Test
    @DisplayName("Given existing book, when update, then return Ok response")
    public void givenExistingBook_whenUpdate_thenReturnOkResponse() {
        final UpdateBookDTO updateBookDTO = new UpdateBookDTO();
        updateBookDTO.setTitle("Oblivion");
        updateBookDTO.setLanguage("english");
        updateBookDTO.setGenre("Fantasy");

        doReturn(new Book())
                .when(bookService)
                .update(eq(1L), any(Book.class), isNull());

        final ResponseEntity<BookDTO> responseEntity = bookController.update(1L, updateBookDTO, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).update(eq(1L), any(Book.class), isNull());
    }

    @Test
    @DisplayName("Given non existent book, when update, then throw Not Found exception")
    public void givenNonExistentBook_whenUpdate_thenReturnThrowNotFoundException() {
        final UpdateBookDTO updateBookDTO = new UpdateBookDTO();
        updateBookDTO.setTitle("Oblivion");
        updateBookDTO.setLanguage("english");
        updateBookDTO.setGenre("Fantasy");

        doThrow(NotFoundException.class)
                .when(bookService)
                .update(eq(1L), any(Book.class), isNull());

        assertThrows(NotFoundException.class, () -> bookController.update(1L, updateBookDTO, null));
        verify(bookService, times(1)).update(eq(1L), any(Book.class), isNull());
    }

    @Test
    @DisplayName("Given existing book, when delete, then return No Content response")
    public void givenExistingBook_whenDelete_thenReturnNoContentResponse() {
        doReturn(new Book())
                .when(bookService)
                .find(1L);

        final ResponseEntity<BookDTO> responseEntity = bookController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(bookService, times(1)).delete(1L);
    }
}
