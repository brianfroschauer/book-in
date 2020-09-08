package com.austral.bookin.controller;

import com.austral.bookin.dto.author.AuthorDTO;
import com.austral.bookin.dto.author.CreateAuthorDTO;
import com.austral.bookin.dto.author.UpdateAuthorDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.specification.AuthorSpecification;
import com.austral.bookin.util.ObjectMapper;
import com.austral.bookin.util.ObjectMapperImpl;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
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
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService authorService;
    private final ObjectMapper objectMapper;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
        objectMapper = new ObjectMapperImpl();
    }

    @GetMapping
    public Page<AuthorDTO> find(@Or({
                                    @Spec(path = "firstName", params = "key", spec = Like.class),
                                    @Spec(path = "lastName", params = "key", spec = Like.class)})
                                Specification<Author> authorSpecification,
                                Pageable pageable) {
        final Page<Author> authors = authorService.findAll(authorSpecification, pageable);
        return authors.map(author -> objectMapper.map(author, AuthorDTO.class));
    }

    @GetMapping("/search")
    public Page<AuthorDTO> findByName(AuthorSpecification authorSpecification, Pageable pageable) {
        final Page<Author> authors = authorService.findAll(authorSpecification, pageable);
        return authors.map(author -> objectMapper.map(author, AuthorDTO.class));
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> find(@PathVariable Long id) {
        final Author author = authorService.find(id);
        return ResponseEntity.ok(objectMapper.map(author, AuthorDTO.class));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<AuthorDTO> create(@RequestPart("author") @Valid CreateAuthorDTO createAuthorDTO,
                                            @RequestPart(value = "photo", required = false) MultipartFile file) {
        final Author author = authorService.save(objectMapper.map(createAuthorDTO, Author.class), file);
        return ResponseEntity.ok(objectMapper.map(author, AuthorDTO.class));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id,
                                            @RequestPart("author") @Valid UpdateAuthorDTO updateAuthorDTO,
                                            @RequestPart(value = "photo", required = false) MultipartFile file) {
        final Author author = authorService.update(id, objectMapper.map(updateAuthorDTO, Author.class), file);
        return ResponseEntity.ok(objectMapper.map(author, AuthorDTO.class));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("{id}")
    public ResponseEntity<AuthorDTO> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}