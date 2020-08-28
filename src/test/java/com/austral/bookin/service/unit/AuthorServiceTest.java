package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Author;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.AuthorRepository;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.specification.AuthorSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private AuthorSpecification authorSpecification;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    public void contextLoads() {
        assertNotNull(authorService);
    }

    @Test
    @DisplayName("Given authors list, when find all, then return authors")
    public void givenAuthors_WhenFindAll_ReturnAuthors() {
        doReturn(Arrays.asList(new Author(), new Author()))
                .when(authorRepository)
                .findAll(authorSpecification);

        final List<Author> authors = authorService.find(authorSpecification);

        verify(authorRepository).findAll(authorSpecification);
        assertEquals(2, authors.size());
    }

    @Test
    @DisplayName("Given empty list, when find all, then return empty list")
    public void givenEmptyList_whenFindAll_ThenReturnEmptyList() {
        doReturn(Collections.emptyList())
                .when(authorRepository)
                .findAll(authorSpecification);

        final List<Author> authors = authorService.find(authorSpecification);

        assertTrue(authors.isEmpty());
        verify(authorRepository).findAll(authorSpecification);
    }

    @Test
    @DisplayName("Given present optional author, when find by id, then return author")
    public void givenPresentOptionalAuthor_whenFindById_ThenReturnAuthor() {
        doReturn(Optional.of(new Author()))
                .when(authorRepository)
                .findById(4L);

        final Author author = authorService.find(4L);

        assertNotNull(author);
        verify(authorRepository).findById(4L);
    }

    @Test
    @DisplayName("Given empty optional author, when find by id, then throw not found exception")
    public void givenEmptyOptionalAuthor_whenFindById_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(authorRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> authorService.find(4L));
        verify(authorRepository).findById(4L);
    }

    @Test
    @DisplayName("Given author, when save, then return author")
    public void givenAuthor_whenSave_thenReturnAuthor() throws IOException {
        doReturn(new Author())
                .when(authorRepository)
                .save(any(Author.class));

        final Author author = authorService.save(new Author(), null);

        assertNotNull(author);
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    @DisplayName("Given present optional author, when update, then update author")
    public void givenPresentOptionalAuthor_whenUpdate_thenUpdateAuthor() {
        doReturn(Optional.of(new Author()))
                .when(authorRepository)
                .findById(4L);

        doReturn(new Author())
                .when(authorRepository)
                .save(any(Author.class));

        final Author author = authorService.update(4L, new Author(), null);

        assertNotNull(author);
        verify(authorRepository).findById(4L);
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    @DisplayName("Given present optional author, when update, then throw not found exception")
    public void givenEmptyOptionalAuthor_whenUpdate_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(authorRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> authorService.update(4L, new Author(), null));
        verify(authorRepository).findById(4L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    @DisplayName("Given present optional author, when delete, then delete author")
    public void givenPresentOptionalAuthor_whenDelete_thenDeleteAuthor() {
        doReturn(Optional.of(new Author()))
                .when(authorRepository)
                .findById(4L);

        authorService.delete(4L);

        verify(authorRepository).delete(any(Author.class));
    }

    @Test
    @DisplayName("Given empty optional author, when delete, then throw not found exception")
    public void givenEmptyOptionalAuthor_whenDelete_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(authorRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> authorService.delete(4L));
        verify(authorRepository).findById(4L);
        verify(authorRepository, never()).delete(any(Author.class));
    }
}