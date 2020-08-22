package com.austral.bookin.controller.integration;

import com.austral.bookin.dto.credentials.CredentialsDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;
import com.austral.bookin.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SignupService signupService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @BeforeEach
    public void setUp() {
        final User user = new User(
                "John",
                "Doe",
                "johndoe@mail.com",
                "password123",
                "M"
        );

        signupService.signup(user);
    }

    @Test
    @DisplayName("Given valid user, when Login, then return Ok response")
    public void givenValidUser_whenLogin_thenReturnOkResponse() throws Exception {

        final CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setEmail("johndoe@mail.com");
        credentialsDTO.setPassword("password123");

        MvcResult result = mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(credentialsDTO)))
                .andReturn();

        final String authorization = result.getResponse().getHeader("Authorization");
        assertNotNull(authorization);
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Given invalid user, when Login, then return Unauthorized response")
    public void givenInvalidUser_whenLogin_thenReturnUnauthorizedResponse() throws Exception {
        final CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setEmail("johndoe@mail.com");
        credentialsDTO.setPassword("notmightypassword");

        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(credentialsDTO)))
                .andExpect(status().isUnauthorized());
    }

    @AfterEach
    public void tearDown() {
        userService.delete(userService.find("johndoe@mail.com").getId());
    }
}