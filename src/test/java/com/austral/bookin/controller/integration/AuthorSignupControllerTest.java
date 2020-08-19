//package com.austral.bookin.controller.integration;
//
//import com.austral.bookin.controller.AuthorController;
//import com.austral.bookin.dto.author.SignupAuthorDTO;
//import com.austral.bookin.entity.Author;
//import com.austral.bookin.service.author.AuthorService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = AuthorController.class)
//public class AuthorSignupControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private AuthorService authorService;
//
//    @Test
//    public void contextLoads() {
//        assertNotNull(mockMvc);
//        assertNotNull(objectMapper);
//    }
//
//    @Test
//    @DisplayName("Given valid author, when signup, then return Ok response")
//    public void givenValidAuthor_whenSignup_thenReturnOkResponse() throws Exception {
//        final SignupAuthorDTO signupAuthorDTO = new SignupAuthorDTO();
//        signupAuthorDTO.setFirstName("firstName");
//        signupAuthorDTO.setLastName("lastName");
//        signupAuthorDTO.setNationality("Argentina");
//        signupAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));
//
//        Mockito.doReturn(new Author())
//                .when(authorService)
//                .save(any(Author.class));
//
//        mockMvc.perform(post("/authors/signup")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(signupAuthorDTO)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Given author with short or empty first name, when signup, then return Bad Request response")
//    public void givenAuthorWithShortFirstName_whenSignup_thenReturnBadRequestResponse() throws Exception {
//        final SignupAuthorDTO signupAuthorDTO = new SignupAuthorDTO();
//        signupAuthorDTO.setFirstName("fi");
//        signupAuthorDTO.setLastName("lastName");
//        signupAuthorDTO.setNationality("Argentina");
//        signupAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));
//
//        Mockito.doReturn(new Author())
//                .when(authorService)
//                .save(any(Author.class));
//
//        mockMvc.perform(post("/authors/signup")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(signupAuthorDTO)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Given author with very long first name, when signup, then return Bad Request response")
//    public void givenAuthorWithVeryLongFirstName_whenSignup_thenReturnBadRequestResponse() throws Exception {
//        final SignupAuthorDTO signupAuthorDTO = new SignupAuthorDTO();
//        signupAuthorDTO.setFirstName("firstNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        signupAuthorDTO.setLastName("lastName");
//        signupAuthorDTO.setNationality("Argentina");
//        signupAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));
//
//        mockMvc
//                .perform(post("/authors/signup")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(signupAuthorDTO)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Given author with short or empty last name, when signup, then return Bad Request response")
//    public void givenAuthorWithShortLastName_whenSignup_thenReturnBadRequestResponse() throws Exception {
//        final SignupAuthorDTO signupAuthorDTO = new SignupAuthorDTO();
//        signupAuthorDTO.setFirstName("firstName");
//        signupAuthorDTO.setLastName("");
//        signupAuthorDTO.setNationality("Argentina");
//        signupAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));
//
//        mockMvc
//                .perform(post("/authors/signup")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(signupAuthorDTO)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Given author with very long last name, when signup, then return Bad Request response")
//    public void givenAuthorWithVeryLongLastName_whenSignup_thenReturnBadRequestResponse() throws Exception {
//        final SignupAuthorDTO signupAuthorDTO = new SignupAuthorDTO();
//        signupAuthorDTO.setFirstName("firstName");
//        signupAuthorDTO.setLastName("lastNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        signupAuthorDTO.setNationality("Argentina");
//        signupAuthorDTO.setBirthday(new Date(100, Calendar.MAY, 6));
//
//        mockMvc
//                .perform(post("/authors/signup")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(signupAuthorDTO)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("Given author with wrong birthday, when signup, then return Bad Request response")
//    public void givenAuthorWithWrongBirthday_whenSignup_thenReturnBadRequestResponse() throws Exception {
//        final SignupAuthorDTO signupAuthorDTO = new SignupAuthorDTO();
//        signupAuthorDTO.setFirstName("firstName");
//        signupAuthorDTO.setLastName("lastName");
//        signupAuthorDTO.setNationality("Argentina");
//        signupAuthorDTO.setBirthday(new Date((2021-1900), Calendar.MAY, 6));
//
//        mockMvc
//                .perform(post("/authors/signup")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(signupAuthorDTO)))
//                .andExpect(status().isBadRequest());
//    }
//}