package com.austral.bookin.controller;

import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.book.CreateBookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.specification.BookSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;
    private final ObjectMapper objectMapper;

    public BookController(BookService bookService) {
        this.bookService = bookService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> find(BookSpecification specification) {
        final List<Book> books = bookService.find(specification);
        return ResponseEntity.ok(objectMapper.map(books, BookDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> find(@PathVariable Long id) {
        final Book book = bookService.find(id);
        return ResponseEntity.ok(objectMapper.map(book, BookDTO.class));
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestPart("book") @Valid CreateBookDTO createBookDTO,
                                          @RequestPart(value = "photo") MultipartFile file) {
        final Book book = bookService.save(objectMapper.map(createBookDTO, Book.class), file);
        return ResponseEntity.ok(objectMapper.map(book, BookDTO.class));
    }
}