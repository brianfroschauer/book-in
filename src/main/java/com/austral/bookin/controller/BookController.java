package com.austral.bookin.controller;

import com.austral.bookin.dto.book.BookDTO;
import com.austral.bookin.dto.book.CreateBookDTO;
import com.austral.bookin.dto.book.UpdateBookDTO;
import com.austral.bookin.entity.Book;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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
    public Page<BookDTO> find(@And({
                                @Spec(path = "title", spec = Like.class)})
                              Specification<Book> bookSpecification, Pageable pageable) {
        final Page<Book> books = bookService.findAll(bookSpecification, pageable);
        return books.map(book -> objectMapper.map(book, BookDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> find(@PathVariable Long id) {
        final Book book = bookService.find(id);
        return ResponseEntity.ok(objectMapper.map(book, BookDTO.class));
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