package com.austral.bookin.controller.integration;

import com.austral.bookin.controller.SignupController;
import com.austral.bookin.dto.user.SignupUserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SignupController.class)
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignupService signupService;

    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @Test
    @DisplayName("Given valid user, when signup, then return Ok response")
    public void givenValidUser_whenSignup_thenReturnOkResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword1");

        Mockito.doReturn(new User())
                .when(signupService)
                .signup(any(User.class));

        mockMvc.perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given user with short or empty first name, when signup, then return Bad Request response")
    public void givenUserWithShortFirstName_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword");

        mockMvc.perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with very long first name, when signup, then return Bad Request response")
    public void givenUserWithVeryLongFirstName_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword1");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with short or empty last name, when signup, then return Bad Request response")
    public void givenUserWithShortLastName_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword1");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with very long last name, when signup, then return Bad Request response")
    public void givenUserWithVeryLongLastName_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword1");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with email without at (@), when signup, then return Bad Request response")
    public void givenUserWithEmailWithoutAtSymbol_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("usergmail.com");
        signupUserDTO.setPassword("P@ssword1");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with email with at (@) in the wrong place, when signup, then return Bad Request response")
    public void givenUserWithEmailWithAtSymbolInWrongPlace_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("usergmail.com@");
        signupUserDTO.setPassword("P@ssword1");

        mockMvc
                .perform(post("/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with email without dot (.), when signup, then return Bad Request response")
    public void givenUserWithEmailWithoutDot_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail");
        signupUserDTO.setPassword("P@ssword1");

        mockMvc
                .perform(post("/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with password without numbers, when signup, then return Bad Request response")
    public void givenUserWithPasswordWithoutNumbers_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with password without special characters, when signup, then return Bad Request response")
    public void givenUserWithPasswordWithoutSpecialCharacters_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("Password1");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with password without letters, when signup, then return Bad Request response")
    public void givenUserWithPasswordWithoutLetters_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("1234567890@");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with very short password, when signup, then return Bad Request response")
    public void givenUserWithVeryShortPassword_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("Pass");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with very long password, when signup, then return Bad Request response")
    public void givenUserWithVeryLongPassword_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword1111111111111111111111111111111111111111111");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }
}
