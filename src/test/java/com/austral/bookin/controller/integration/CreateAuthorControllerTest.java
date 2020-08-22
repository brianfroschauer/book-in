package com.austral.bookin.controller.integration;

import com.austral.bookin.controller.AuthorController;
import com.austral.bookin.dto.author.CreateAuthorDTO;
import com.austral.bookin.entity.Author;
import com.austral.bookin.service.author.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthorController.class)
public class CreateAuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @Test
    @DisplayName("Given valid author, when create, then return Ok response")
    public void givenValidAuthor_whenCreate_thenReturnOkResponse() throws Exception {
        final CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO();
        createAuthorDTO.setFirstName("firstName");
        createAuthorDTO.setLastName("lastName");
        createAuthorDTO.setNationality("Argentina");
        createAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));

        Mockito.doReturn(new Author())
                .when(authorService)
                .save(any(Author.class), isNull());

        MockMultipartFile author = new MockMultipartFile("author", "", "application/json", objectMapper.writeValueAsString(createAuthorDTO).getBytes());

        mockMvc
                .perform(MockMvcRequestBuilders.multipart("/authors")
                        .file(author))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given author with short or empty first name, when create, then return Bad Request response")
    public void givenAuthorWithShortFirstName_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO();
        createAuthorDTO.setFirstName("");
        createAuthorDTO.setLastName("lastName");
        createAuthorDTO.setNationality("Argentina");
        createAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));

        Mockito.doReturn(new Author())
                .when(authorService)
                .save(any(Author.class), isNull());

        mockMvc
                .perform(post("/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createAuthorDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given author with very long first name, when create, then return Bad Request response")
    public void givenAuthorWithVeryLongFirstName_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO();
        createAuthorDTO.setFirstName("firstNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        createAuthorDTO.setLastName("lastName");
        createAuthorDTO.setNationality("Argentina");
        createAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));

        mockMvc
                .perform(post("/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createAuthorDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given author with short or empty last name, when create, then return Bad Request response")
    public void givenAuthorWithShortLastName_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO();
        createAuthorDTO.setFirstName("firstName");
        createAuthorDTO.setLastName("");
        createAuthorDTO.setNationality("Argentina");
        createAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));

        mockMvc
                .perform(post("/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createAuthorDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given author with very long last name, when create, then return Bad Request response")
    public void givenAuthorWithVeryLongLastName_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO();
        createAuthorDTO.setFirstName("firstName");
        createAuthorDTO.setLastName("lastNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        createAuthorDTO.setNationality("Argentina");
        createAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));

        mockMvc
                .perform(post("/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createAuthorDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given author with wrong birthday, when create, then return Bad Request response")
    public void givenAuthorWithWrongBirthday_whenCreate_thenReturnBadRequestResponse() throws Exception {
        final CreateAuthorDTO createAuthorDTO = new CreateAuthorDTO();
        createAuthorDTO.setFirstName("firstName");
        createAuthorDTO.setLastName("lastName");
        createAuthorDTO.setNationality("Argentina");
        createAuthorDTO.setBirthday(new Date((2021-1900), Calendar.MAY, 6));

        mockMvc
                .perform(post("/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createAuthorDTO)))
                .andExpect(status().isBadRequest());
    }
}