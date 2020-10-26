package com.austral.bookin.controller.integration;

import com.austral.bookin.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private UserController userController;

    @Test
    @DisplayName("Given null principal, then return null")
    public void givenNullPrincipal_thenReturnNull() {

        assert userController.find((Principal) null) == null;
    }
}
