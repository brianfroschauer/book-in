package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.BookAuthorController;
import com.austral.bookin.dto.author.SearchAuthorDTO;
import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.author.AuthorService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BookAuthorControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private BookAuthorController bookAuthorController;

    @Test
    public void contextLoads() {
        assertNotNull(bookAuthorController);
    }

    @Test
    @DisplayName("Given book id get authors, then return OK response")
    public void givenBookIdGetAuthors_thenReturnOkResponse() {
        Author author = new Author("firstName", "lastName", "US", new Date());
        Author author2 = new Author("firstName2", "lastName2", "GB", new Date());

        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        Mockito.doReturn(authors)
                .when(authorService)
                .findByBook(1L);

        final ResponseEntity<List<SearchAuthorDTO>> responseEntity = bookAuthorController.findByBook(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(authorService, times(1)).findByBook(1L);
    }

    @Test
    @DisplayName("Given author id get books, then return OK response")
    public void givenAuthorIdGetBooks_thenReturnOkResponse() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        Book book2 = new Book(2L, "title2", "Aventura", "en", new Date(), new ArrayList<>());
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);

        Mockito.doReturn(books)
                .when(bookService)
                .findByAuthor(3L);

        final ResponseEntity<List<BookDTO>> responseEntity = bookAuthorController.findByAuthor(3L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(bookService, times(1)).findByAuthor(3L);
    }
}
