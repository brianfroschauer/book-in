package com.austral.bookin.controller;

import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.book.CreateBookDTO;
import com.austral.bookin.dto.book.UpdateBookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.specification.BookSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
        final List<BookDTO> booksDTO = objectMapper.map(books, BookDTO.class);
        booksDTO.forEach(book -> book.setStars(bookService.getStars(book.getId())));
        return ResponseEntity.ok(booksDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> find(@PathVariable Long id) {
        final Book book = bookService.find(id);
        final BookDTO bookDTO = objectMapper.map(book, BookDTO.class);
        bookDTO.setStars(bookService.getStars(id));
        return ResponseEntity.ok(bookDTO);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestPart("book") @Valid CreateBookDTO createBookDTO,
                                          @RequestPart(value = "photo") MultipartFile file) {
        final Book book = bookService.save(objectMapper.map(createBookDTO, Book.class), file);
        return ResponseEntity.ok(objectMapper.map(book, BookDTO.class));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id,
                                          @RequestPart("book") @Valid UpdateBookDTO updateBookDTO,
                                          @RequestPart(value = "photo", required = false) MultipartFile file) {
        final Book book = bookService.update(id, objectMapper.map(updateBookDTO, Book.class), file);
        return ResponseEntity.ok(objectMapper.map(book, BookDTO.class));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<BookDTO> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}