package com.austral.bookin.controller;

import com.austral.bookin.dto.author.AuthorDTO;
import com.austral.bookin.dto.author.CreateAuthorDTO;
import com.austral.bookin.dto.author.UpdateAuthorDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.specification.AuthorSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ObjectMapper objectMapper;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> find(AuthorSpecification specification) {
        final List<Author> authors = authorService.find(specification);
        return ResponseEntity.ok(objectMapper.map(authors, AuthorDTO.class));
    }

    @PostMapping()
    public ResponseEntity<AuthorDTO> create(@RequestPart("author") CreateAuthorDTO createAuthorDTO,
                                            @RequestPart(value = "photo", required = false) MultipartFile file) throws IOException {
        final Author author = authorService.save(objectMapper.map(createAuthorDTO, Author.class), file);
        return ResponseEntity.ok(objectMapper.map(author, AuthorDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> find(@PathVariable Long id) {
        final Author author = authorService.find(id);
        return ResponseEntity.ok(objectMapper.map(author, AuthorDTO.class));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id,
                                            @RequestPart("author") UpdateAuthorDTO updateAuthorDTO,
                                            @RequestPart(value = "photo", required = false) MultipartFile file) {
        final Author author = authorService.update(id, objectMapper.map(updateAuthorDTO, Author.class), file);
        return ResponseEntity.ok(objectMapper.map(author, AuthorDTO.class));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AuthorDTO> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}