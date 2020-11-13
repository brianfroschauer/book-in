package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.BookController;
import com.austral.bookin.dto.book.*;
import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.specification.BookSpecification;
import com.austral.bookin.specification.SearchBookSpecification;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private VelocityEngine velocityEngine;

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
    @DisplayName("Given book spec, then return OK response")
    public void givenBookSpec_thenReturnOkResponse() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        Book book2 = new Book(2L, "title2", "Aventura", "en", new Date(), new ArrayList<>());
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);

        BookSpecification specification = new BookSpecification() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        Mockito.doReturn(books)
                .when(bookService)
                .findAll(specification, PageRequest.of(1, 2));

        final ResponseEntity<List<SearchBookDTO>> responseEntity = bookController.find(specification, 1, 2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).findAll(specification, PageRequest.of(1, 2));
    }

    @Test
    @DisplayName("Given search book spec, then return OK response")
    public void givenSearchBookSpec_thenReturnOkResponse() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        Book book2 = new Book(2L, "title2", "Aventura", "en", new Date(), new ArrayList<>());
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);

        SearchBookSpecification specification = new SearchBookSpecification() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        Mockito.doReturn(books)
                .when(bookService)
                .findAll(specification, PageRequest.of(1, 2));

        final ResponseEntity<List<SearchBookWithAuthorsDTO>> responseEntity = bookController.find(specification, 1, 2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).findAll(specification, PageRequest.of(1, 2));
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

        final ResponseEntity<List<BookWithAuthorsDTO>> responseEntity = bookController.sortByStars(2);

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

        final ResponseEntity<List<BookWithAuthorsDTO>> responseEntity = bookController.sortByGenre("Aventura", 2);

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
        updateBookDTO.setAuthors(new ArrayList<>());

        doReturn(new Book())
                .when(bookService)
                .update(eq(1L), any(Book.class), anyList(),isNull());

        final ResponseEntity<BookDTO> responseEntity = bookController.update(1L, updateBookDTO, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).update(eq(1L), any(Book.class), anyList(), isNull());
    }

    @Test
    @DisplayName("Given non existent book, when update, then throw Not Found exception")
    public void givenNonExistentBook_whenUpdate_thenReturnThrowNotFoundException() {
        final UpdateBookDTO updateBookDTO = new UpdateBookDTO();
        updateBookDTO.setTitle("Oblivion");
        updateBookDTO.setLanguage("english");
        updateBookDTO.setGenre("Fantasy");
        updateBookDTO.setAuthors(new ArrayList<>());

        doThrow(NotFoundException.class)
                .when(bookService)
                .update(eq(1L), any(Book.class), anyList(), isNull());

        assertThrows(NotFoundException.class, () -> bookController.update(1L, updateBookDTO, null));
        verify(bookService, times(1)).update(eq(1L), any(Book.class), anyList(), isNull());
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
