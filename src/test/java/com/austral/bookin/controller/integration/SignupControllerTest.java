package com.austral.bookin.controller.integration;

import com.austral.bookin.dto.user.SignupUserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SignupControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SignupService signupService;

    @MockBean
    private VelocityEngine velocityEngine;

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
        signupUserDTO.setPassword("P@ssword1123");
        signupUserDTO.setGender("M");

        final User user = new User("firstName", "lastName", "user@gmail.com", "P@ssword1123", "M", new HashSet<>());

        Mockito.doReturn(user)
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
        signupUserDTO.setFirstName("");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("use2r@gmail.com");
        signupUserDTO.setPassword("1234567890@");
        signupUserDTO.setGender("M");

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
        signupUserDTO.setEmail("use3r@gmail.com");
        signupUserDTO.setPassword("Password123");
        signupUserDTO.setGender("M");

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
        signupUserDTO.setGender("M");

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
        signupUserDTO.setPassword("P@ssword123");
        signupUserDTO.setGender("M");

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
        signupUserDTO.setPassword("P@ssword1231");
        signupUserDTO.setGender("M");

        mockMvc
                .perform(post("/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with long lastname on sign up return bad request")
    public void givenUserwithLongLastName_onSignup_returnbadrequest() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        signupUserDTO.setEmail("user@gmail");
        signupUserDTO.setPassword("P@ssword1231");
        signupUserDTO.setGender("M");

        mockMvc
                .perform(post("/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with long firstname on sign up return bad request")
    public void givenUserwithLongFirstName_onSignup_returnbadrequest() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstNameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail");
        signupUserDTO.setPassword("P@ssword1231");
        signupUserDTO.setGender("M");

        mockMvc
                .perform(post("/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with email without dot (.), when signup, then return Bad3 Request response")
    public void givenUserWithEmailWithoutDot_whenSignup_thenReturnBadRequestResponse3() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail");
        signupUserDTO.setPassword("P@ssword1231");
        signupUserDTO.setGender("M");

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
        signupUserDTO.setGender("M");

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
        signupUserDTO.setGender("M");

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
        signupUserDTO.setPassword("Pas1");
        signupUserDTO.setGender("M");

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
        signupUserDTO.setEmail("user123@gmail.com");
        signupUserDTO.setPassword("P@ssword1111111111111111111111111111111111111111111");
        signupUserDTO.setGender("M");

        mockMvc
                .perform(post("/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given user with invalid gender, when signup, then return Bad Request response")
    public void givenUserWithInvalidGender_whenSignup_thenReturnBadRequestResponse() throws Exception {
        final SignupUserDTO signupUserDTO = new SignupUserDTO();
        signupUserDTO.setFirstName("firstName");
        signupUserDTO.setLastName("lastName");
        signupUserDTO.setEmail("user@gmail.com");
        signupUserDTO.setPassword("P@ssword11");
        signupUserDTO.setGender("L"); //Must be M F OR A

        mockMvc
                .perform(post("/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupUserDTO)))
                .andExpect(status().isBadRequest());
    }
}
