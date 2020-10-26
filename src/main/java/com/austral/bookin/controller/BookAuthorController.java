package com.austral.bookin.controller;

import com.austral.bookin.dto.author.SearchAuthorDTO;
import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookAuthorController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final ObjectMapper objectMapper;

    public BookAuthorController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping("/authors/{id}/books")
    public ResponseEntity<List<BookDTO>> findByAuthor(@PathVariable Long id) {
        final List<Book> books = bookService.findByAuthor(id);
        return ResponseEntity.ok(objectMapper.map(books, BookDTO.class));
    }

    @GetMapping("/books/{id}/authors")
    public ResponseEntity<List<SearchAuthorDTO>> findByBook(@PathVariable Long id) {
        final List<Author> authors = authorService.findByBook(id);
        return ResponseEntity.ok(objectMapper.map(authors, SearchAuthorDTO.class));
    }
}