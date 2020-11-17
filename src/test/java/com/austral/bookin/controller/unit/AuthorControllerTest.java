package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.AuthorController;
import com.austral.bookin.dto.author.AuthorDTO;
import com.austral.bookin.dto.author.SearchAuthorDTO;
import com.austral.bookin.dto.author.UpdateAuthorDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.specification.AuthorSpecification;
import com.austral.bookin.specification.SearchAuthorSpecification;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorController authorController;

    @MockBean
    private VelocityEngine velocityEngine;

    @Test
    public void contextLoads() {
        assertNotNull(authorController);
    }

    @Test
    @DisplayName("Given existing author id, when find by ID, then return Ok response")
    public void givenExistingAuthorId_whenFindById_thenReturnOkResponse() {
        Mockito.doReturn(new Author())
                .when(authorService)
                .find(1L);

        final ResponseEntity<AuthorDTO> responseEntity = authorController.find(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(authorService, times(1)).find(1L);
    }

    @Test
    @DisplayName("Given non existent author id, when find by ID, then throw Not Found exception")
    public void givenNonExistentAuthorId_whenFindById_thenReturnNotFoundResponse() {
        Mockito.doThrow(NotFoundException.class)
                .when(authorService)
                .find(1L);

        assertThrows(NotFoundException.class, () -> authorController.find(1L));
    }

    @Test
    @DisplayName("Given author spec, then return OK response")
    public void givenAuthorSpec_thenReturnOkResponse() {
        Author author = new Author("firstName", "lastName", "US", new Date());
        Author author2 = new Author("firstName2", "lastName2", "GB", new Date());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        AuthorSpecification specification = new AuthorSpecification() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        Mockito.doReturn(authors)
                .when(authorService)
                .findAll(specification, PageRequest.of(1, 2));

        final ResponseEntity<List<SearchAuthorDTO>> responseEntity = authorController.find(specification, 1, 2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(authorService, times(1)).findAll(specification, PageRequest.of(1, 2));
    }

    @Test
    @DisplayName("Given search author spec, then return OK response")
    public void givenSearchAuthorSpec_thenReturnOkResponse() {
        Author author = new Author("firstName", "lastName", "US", new Date());
        Author author2 = new Author("firstName2", "lastName2", "GB", new Date());
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        SearchAuthorSpecification specification = new SearchAuthorSpecification() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };

        Mockito.doReturn(authors)
                .when(authorService)
                .findAll(specification, PageRequest.of(1, 2));

        final ResponseEntity<List<AuthorDTO>> responseEntity = authorController.find(specification, 1, 2);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(authorService, times(1)).findAll(specification, PageRequest.of(1, 2));
    }

    @Test
    @DisplayName("Given existing author, when update, then return Ok response")
    public void givenExistingAuthor_whenUpdate_thenReturnOkResponse() {
        final UpdateAuthorDTO updateAuthorDTO = new UpdateAuthorDTO();
        updateAuthorDTO.setFirstName("firstName");
        updateAuthorDTO.setLastName("lastName");

        doReturn(new Author())
                .when(authorService)
                .update(eq(1L), any(Author.class), isNull());

        final ResponseEntity<AuthorDTO> responseEntity = authorController.update(1L, updateAuthorDTO, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(authorService, times(1)).update(eq(1L), any(Author.class), isNull());
    }

    @Test
    @DisplayName("Given non existent author, when update, then throw Not Found exception")
    public void givenNonExistentAuthor_whenUpdate_thenReturnThrowNotFoundException() {
        final UpdateAuthorDTO updateAuthorDTO = new UpdateAuthorDTO();
        updateAuthorDTO.setFirstName("firstName");
        updateAuthorDTO.setLastName("lastName");

        doThrow(NotFoundException.class)
                .when(authorService)
                .update(eq(1L), any(Author.class), isNull());

        assertThrows(NotFoundException.class, () -> authorController.update(1L, updateAuthorDTO, null));
        verify(authorService, times(1)).update(eq(1L), any(Author.class), isNull());
    }

    @Test
    @DisplayName("Given existing author, when delete, then return No Content response")
    public void givenExistingAuthor_whenDelete_thenReturnNoContentResponse() {
        doReturn(new Author())
                .when(authorService)
                .find(1L);

        final ResponseEntity<AuthorDTO> responseEntity = authorController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(authorService, times(1)).delete(1L);
    }
}
