package com.austral.bookin.service.unit;

import com.austral.bookin.entity.User;
import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.service.signup.SignupService;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SignupServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SignupService signupService;

    @MockBean
    private VelocityEngine velocityEngine;

    @Test
    public void contextLoads() {
        assertNotNull(encoder);
        assertNotNull(signupService);
    }

    @Test
    @DisplayName("Given user, when signup, then return user")
    public void givenUser_whenSignup_thenReturnUser() {
        final User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("password");

        doReturn(user)
                .when(userRepository)
                .save(user);

        assertNotNull(signupService.signup(user));
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Given user with an email that already exists, when signup, then throw already exists exception")
    public void givenUserWithEMailThatAlreadyExists_whenSignup_thenThrowAlreadyExistsException() {
        final User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("password");

        doReturn(Optional.of(user))
                .when(userRepository)
                .findByEmail("user@gmail.com");

        assertThrows(AlreadyExistsException.class, () -> signupService.signup(user));
    }

    @Test
    @DisplayName("Given user, when signup, then encode password")
    public void givenUser_whenSignup_thenEncodePassword() {
        final User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("password");

        doReturn(user)
                .when(userRepository)
                .save(user);

        assertNotNull(signupService.signup(user));
        assertTrue(encoder.matches("password", user.getPassword()));
    }
}
