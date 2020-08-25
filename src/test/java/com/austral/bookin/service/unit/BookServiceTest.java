package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.specification.BookSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookSpecification bookSpecification;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void contextLoads() {
        assertNotNull(bookService);
    }

    @Test
    @DisplayName("Given books list, when find all, then return books")
    public void givenBoooks_WhenFindAll_ReturnBooks() {
        doReturn(Arrays.asList(new Book(), new Book()))
                .when(bookRepository)
                .findAll(bookSpecification);

        final List<Book> books = bookService.find(bookSpecification);

        verify(bookRepository).findAll(bookSpecification);
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Given empty list, when find all, then return empty list")
    public void givenEmptyList_whenFindAll_ThenReturnEmptyList() {
        doReturn(Collections.emptyList())
                .when(bookRepository)
                .findAll(bookSpecification);

        final List<Book> books = bookService.find(bookSpecification);

        assertTrue(books.isEmpty());
        verify(bookRepository).findAll(bookSpecification);
    }

    @Test
    @DisplayName("Given present optional book, when find by id, then return book")
    public void givenPresentOptionalBook_whenFindById_ThenReturnBook() {
        doReturn(Optional.of(new Book()))
                .when(bookRepository)
                .findById(4L);

        final Book book = bookService.find(4L);

        assertNotNull(book);
        verify(bookRepository).findById(4L);
    }

    @Test
    @DisplayName("Given empty optional book, when find by id, then throw not found exception")
    public void givenEmptyOptionalBook_whenFindById_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> bookService.find(4L));
        verify(bookRepository).findById(4L);
    }

    @Test
    @DisplayName("Given book, when save, then return book")
    public void givenBook_whenSave_thenReturnBook() {
        doReturn(new Book())
                .when(bookRepository)
                .save(any(Book.class));

        final Book book = bookService.save(new Book(), null);

        assertNotNull(book);
        verify(bookRepository).save(any(Book.class));
    }
}