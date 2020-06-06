package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.SignupController;
import com.austral.bookin.dto.user.SignupUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.signup.SignupService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SignupControllerTest {

    @MockBean
    private SignupService signupService;

    @Autowired
    private SignupController signupController;

    @Test
    public void contextLoads() {
        assertNotNull(signupController);
    }

    @Test
    @DisplayName("Given valid user, when signup, then return Ok response")
    public void givenUser_whenSignup_thenReturnOkResponse() {
        Mockito.doReturn(new User())
                .when(signupService)
                .signup(any(User.class));

        final ResponseEntity<UserDTO> response = signupController.signup(new SignupUserDTO());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(signupService, times(1)).signup(any(User.class));
    }
}
