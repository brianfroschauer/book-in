package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.AuthorController;
import com.austral.bookin.dto.author.AuthorDTO;
import com.austral.bookin.dto.author.UpdateAuthorDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.specification.AuthorSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class AuthorControllerTest {

    @Mock
    private AuthorSpecification authorSpecification;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorController authorController;

    @Test
    public void contextLoads() {
        assertNotNull(authorController);
    }

    @Test
    @DisplayName("When find all, then return Ok response")
    public void whenFindAll_thenReturnOkResponse() {
        doReturn(Collections.emptyList())
                .when(authorService)
                .find(authorSpecification);

        final ResponseEntity<List<AuthorDTO>> responseEntity = authorController.find(authorSpecification);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
        verify(authorService, times(1)).find(authorSpecification);
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