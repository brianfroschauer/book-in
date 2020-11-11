package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Token;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.ExpiredTokenException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.TokenRepository;
import com.austral.bookin.service.token.TokenService;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private VelocityEngine velocityEngine;

    @Test
    public void contextLoads() {
        assertNotNull(tokenService);
    }

    @Test
    @DisplayName("Given present optional token, when find by id, then return token")
    public void givenPresentOptionalToken_whenFindById_ThenReturnToken() {
        doReturn(Optional.of(new Token()))
                .when(tokenRepository)
                .findById(4L);

        final Token token = tokenService.find(4L);

        assertNotNull(token);
        verify(tokenRepository).findById(4L);
    }

    @Test
    @DisplayName("Given empty optional token, when find by id, then throw not found exception")
    public void givenEmptyOptionalToken_whenFindById_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(tokenRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> tokenService.find(4L));
        verify(tokenRepository).findById(4L);
    }

    @Test
    @DisplayName("Given present optional token, when find by token, then return token")
    public void givenPresentOptionalToken_whenFindByToken_ThenReturnToken() {
        doReturn(Optional.of(new Token()))
                .when(tokenRepository)
                .findByToken("as123f");

        final Token token = tokenService.find("as123f");

        assertNotNull(token);
        verify(tokenRepository).findByToken("as123f");
    }

    @Test
    @DisplayName("Given present optional token, when find by token, then throw not found exception")
    public void givenPresentOptionalToken_whenFindByToken_ThenThrowNotFonudException() {
        doThrow(NotFoundException.class)
                .when(tokenRepository)
                .findByToken("as123f");

        assertThrows(NotFoundException.class, () -> tokenService.find("as123f"));
        verify(tokenRepository).findByToken("as123f");
    }

    @Test
    @DisplayName("Given present optional token, when find by user, then return token")
    public void givenPresentOptionalToken_whenFindByUser_ThenReturnToken() {

        User user = new User(1L, "Katia", "Cammisa", "katia@gmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        doReturn(Optional.of(new Token()))
                .when(tokenRepository)
                .findByUser(user);

        final Token token = tokenService.find(user);

        assertNotNull(token);
        verify(tokenRepository).findByUser(user);
    }

    @Test
    @DisplayName("Given present optional token, when find by user, then throw not found exception")
    public void givenPresentOptionalToken_whenFindByUser_ThenThrowNotFoundException() {

        User user = new User(1L, "Katia", "Cammisa", "katia@gmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        doThrow(NotFoundException.class)
                .when(tokenRepository)
                .findByUser(user);

        assertThrows(NotFoundException.class, () -> tokenService.find(user));
        verify(tokenRepository).findByUser(user);
    }

    @Test
    @DisplayName("Save new token for given user, then return it")
    public void saveTokenForUser_ThenReturnIt() {

        User user = new User(1L, "Katia", "Cammisa", "katia@gmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "asd12f", user, new Date(121, Calendar.NOVEMBER, 13));

        Mockito.doReturn(token)
                .when(tokenRepository)
                .save(any(Token.class));

        final Token result = tokenService.createPasswordResetToken(user);

        assertNotNull(result);
        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    @DisplayName("Given valid token, delete it")
    public void givenValidToken_DeleteIt() {

        User user = new User(1L, "Katia", "Cammisa", "katia@gmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "asd12f", user, new Date(121, Calendar.NOVEMBER, 13));

        tokenService.validateToken(token);
        verify(tokenRepository).delete(token);
    }

    @Test
    @DisplayName("Given invalid token, then throw exception")
    public void givenInvalidToken_ThrowException() {

        User user = new User(1L, "Katia", "Cammisa", "katia@gmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "asd12f", user, new Date(115, Calendar.NOVEMBER, 13));

        assertThrows(ExpiredTokenException.class, () -> tokenService.validateToken(token));
    }

    @Test
    @DisplayName("Given present optional token, when delete, then delete token")
    public void givenPresentOptionalToken_whenDelete_thenDeleteToken() {
        doReturn(Optional.of(new Token()))
                .when(tokenRepository)
                .findById(4L);

        tokenService.delete(tokenService.find(4L));

        verify(tokenRepository).delete(any(Token.class));
    }
}
