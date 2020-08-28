package com.austral.bookin.controller;

import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.entity.Book;
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
    private final ObjectMapper objectMapper;

    public BookAuthorController(BookService bookService) {
        this.bookService = bookService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping("/authors/{id}/books")
    public ResponseEntity<List<BookDTO>> findByAuthor(@PathVariable Long id) {
        List<Book> books = bookService.findByAuthor(id);
        return ResponseEntity.ok(objectMapper.map(books, BookDTO.class));
    }
}