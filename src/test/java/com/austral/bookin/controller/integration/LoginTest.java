package com.austral.bookin.controller.integration;

import com.austral.bookin.dto.user.LoginUserDTO;
import com.austral.bookin.dto.user.SignupUserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    //User admin = new User("Pedro","Garde","pedro@hotmail.com", encoder.encode("mightypassword"),"M"); from populate
    @Test
    @DisplayName("Given valid user, when Login, then return Ok response")
    public void givenValidUser_whenLogin_thenReturnOkResponse() throws Exception {
            final LoginUserDTO loginUserDTO = new LoginUserDTO();
            loginUserDTO.setEmail("pedro@hotmail.com");
            loginUserDTO.setPassword("mightypassword");


        MvcResult result = mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginUserDTO)))
                .andReturn();

        String authheader = result.getResponse().getHeader("Authorization");
        assertNotNull(authheader);
        assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    @DisplayName("Given invalid user, when Login, then return Unauthorized response")
    public void giveninValidUser_whenLogin_thenReturnOkResponse() throws Exception {
        final LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail("pedro@hotmail.com");
        loginUserDTO.setPassword("notmightypassword");


        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(loginUserDTO)))
                .andExpect(status().isUnauthorized())
        ;
    }

}