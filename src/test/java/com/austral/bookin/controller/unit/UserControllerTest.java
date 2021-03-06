package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.UserController;
import com.austral.bookin.dto.user.ChangeUserPasswordDTO;
import com.austral.bookin.dto.user.UpdateUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.dto.user.UserNewPasswordDTO;
import com.austral.bookin.entity.Token;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.ExpiredTokenException;
import com.austral.bookin.exception.InvalidOldPasswordException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.service.token.TokenService;
import com.austral.bookin.service.user.UserService;
import com.austral.bookin.specification.UserSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserSpecification userSpecification;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private UserController userController;

    @Test
    public void contextLoads() {
        assertNotNull(userController);
    }

    @Test
    @DisplayName("When find all, then return Ok response")
    public void whenFindAll_thenReturnOkResponse() {
        doReturn(Collections.emptyList())
                .when(userService)
                .find(userSpecification);

        final ResponseEntity<List<UserDTO>> responseEntity = userController.find(userSpecification);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isEmpty());
        verify(userService, times(1)).find(userSpecification);
    }

    @Test
    @DisplayName("Given existing user id, when find by ID, then return Ok response")
    public void givenExistingUserId_whenFindById_thenReturnOkResponse() {
        Mockito.doReturn(new User())
                .when(userService)
                .find(1L);

        final ResponseEntity<UserDTO> responseEntity = userController.find(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(userService, times(1)).find(1L);
    }

    @Test
    @DisplayName("Given non existent user id, when find by ID, then throw Not Found exception")
    public void givenNonExistentUserId_whenFindById_thenReturnNotFoundResponse() {
        Mockito.doThrow(NotFoundException.class)
                .when(userService)
                .find(1L);

        assertThrows(NotFoundException.class, () -> userController.find(1L));
    }

    @Test
    @DisplayName("Change password of given user, then return Ok response")
    public void changePasswordOfUser_thenReturnOkResponse() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        User user2 = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "hola1234", "F", new HashSet<>(), new byte[4], new ArrayList<>());

        Mockito.doReturn(user)
                .when(userService)
                .find("katia@hotmail.com");

        Mockito.doReturn(user2)
                .when(userService)
                .updatePassword("password123", "hola1234", user);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.doReturn("katia@hotmail.com")
                .when(authentication)
                .getName();

        ChangeUserPasswordDTO change = new ChangeUserPasswordDTO("password123", "hola1234");

        final ResponseEntity<UserDTO> responseEntity = userController.changeUserPassword(change);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(userService, times(1)).find("katia@hotmail.com");
        verify(userService, times(1)).updatePassword("password123", "hola1234", user);
    }

    @Test
    @DisplayName("Change password of given user, then return bad request")
    public void changePasswordOfUser_thenReturnBadRequest() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());

        Mockito.doReturn(user)
                .when(userService)
                .find("katia@hotmail.com");

        Mockito.doThrow(new InvalidOldPasswordException())
                .when(userService)
                .updatePassword("password1234", "hola1234", user);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito.doReturn("katia@hotmail.com")
                .when(authentication)
                .getName();

        ChangeUserPasswordDTO change = new ChangeUserPasswordDTO("password1234", "hola1234");

        assertThrows(ResponseStatusException.class, () -> userController.changeUserPassword(change));
        verify(userService, times(1)).find("katia@hotmail.com");
        verify(userService, times(1)).updatePassword("password1234", "hola1234", user);
    }

    @Test
    @DisplayName("Send mail to password of given user, then return OK response")
    public void resetPasswordOfUser_thenReturnOkResponse() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "token12abc", user, new Date(125, Calendar.NOVEMBER, 1));

        Mockito.doReturn(user)
                .when(userService)
                .find("katia@hotmail.com");

        Mockito.doReturn(token)
                .when(tokenService)
                .createPasswordResetToken(user);

        final HttpStatus status = userController.resetUserPassword("katia@hotmail.com");

        assertEquals(HttpStatus.OK, status);
        verify(userService, times(1)).find("katia@hotmail.com");
        verify(tokenService, times(1)).createPasswordResetToken(user);
    }

    @Test
    @DisplayName("Try to send mail to password of given user not found, then throw Not Found Exception")
    public void resetPasswordOfUser_thenThrowNotFound() {

        Mockito.doThrow(NotFoundException.class)
                .when(userService)
                .find("katia1@hotmail.com");

        assertThrows(ResponseStatusException.class, () -> userController.resetUserPassword("katia1@hotmail.com"));
        verify(userService, times(1)).find("katia1@hotmail.com");
    }

    @Test
    @DisplayName("Try to send mail to password of given user not found, then throw Internal Server Error")
    public void resetPasswordOfUser_thenThrowInternalServerError() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());

        Mockito.doReturn(user)
                .when(userService)
                .find("katia@hotmail.com");

        Mockito.doThrow(RuntimeException.class)
                .when(tokenService)
                .createPasswordResetToken(user);

        assertThrows(ResponseStatusException.class, () -> userController.resetUserPassword("katia@hotmail.com"));
        verify(userService, times(1)).find("katia@hotmail.com");
    }

    @Test
    @DisplayName("Receive token to reset password of given user, verify it, then return user")
    public void receiveToken_Verify_thenReturnUser() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "token12abc", user, new Date(125, Calendar.NOVEMBER, 1));

        Mockito.doReturn(token)
                .when(tokenService)
                .find("token12abc");

        final ResponseEntity<UserDTO> response = userController.validateToken("token12abc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tokenService, times(1)).find("token12abc");
        verify(tokenService, times(1)).validateToken(token);
    }

    @Test
    @DisplayName("Receive expired token to reset password of given user, verify it, then return Not Found")
    public void receiveToken_Verify_thenReturnNotFound() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());

        Mockito.doThrow(NotFoundException.class)
                .when(tokenService)
                .find("token123abc");

        assertThrows(ResponseStatusException.class, () -> userController.validateToken("token123abc"));
        verify(tokenService, times(1)).find("token123abc");
    }

    @Test
    @DisplayName("Receive expired token to reset password of given user, verify it, then return Bad Request")
    public void receiveToken_Verify_thenReturnBadRequest() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "token12abc", user, new Date(115, Calendar.NOVEMBER, 1));

        Mockito.doReturn(token)
                .when(tokenService)
                .find("token12abc");

        Mockito.doThrow(ExpiredTokenException.class)
                .when(tokenService)
                .validateToken(token);

        assertThrows(ResponseStatusException.class, () -> userController.validateToken("token12abc"));
        verify(tokenService, times(1)).find("token12abc");
        verify(tokenService, times(1)).validateToken(token);
    }

    @Test
    @DisplayName("Reset password, then return OK response")
    public void resetPassword_thenReturnOkResponse() {
        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        User user2 = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", "password1234", "F", new HashSet<>(), new byte[4], new ArrayList<>());
        UserNewPasswordDTO newPasswordDTO = new UserNewPasswordDTO(user.getId(), "password1234");

        Mockito.doReturn(user2)
                .when(userService)
                .setPassword(1L, "password1234");

        final ResponseEntity<UserDTO> responseEntity = userController.setNewPassword(newPasswordDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).setPassword(1L, "password1234");
    }

    @Test
    @DisplayName("Given existing user, when update, then return Ok response")
    public void givenExistingUser_whenUpdate_thenReturnOkResponse() {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("firstName");
        updateUserDTO.setLastName("lastName");

        doReturn(new User())
                .when(userService)
                .update(eq(1L), any(User.class), isNull());

        final ResponseEntity<UserDTO> responseEntity = userController.update(1L, updateUserDTO, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(userService, times(1)).update(eq(1L), any(User.class), isNull());
    }

    @Test
    @DisplayName("Given non existent user, when update, then throw Not Found exception")
    public void givenNonExistentUser_whenUpdate_thenReturnThrowNotFoundException() {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("firstName");
        updateUserDTO.setLastName("lastName");

        doThrow(NotFoundException.class)
                .when(userService)
                .update(eq(1L), any(User.class), isNull());

        assertThrows(ResponseStatusException.class, () -> userController.update(1L, updateUserDTO, null));
        verify(userService, times(1)).update(eq(1L), any(User.class), isNull());
    }

    @Test
    @DisplayName("Given already used mail, when update, then throw Already Exists Exception")
    public void givenNonExistentUser_whenUpdate_thenReturnThrowAlreadyExistsException() {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("firstName");
        updateUserDTO.setLastName("lastName");

        doThrow(AlreadyExistsException.class)
                .when(userService)
                .update(eq(1L), any(User.class), isNull());

        assertThrows(ResponseStatusException.class, () -> userController.update(1L, updateUserDTO, null));
        verify(userService, times(1)).update(eq(1L), any(User.class), isNull());
    }

    @Test
    @DisplayName("Given existing user, when delete, then return No Content response")
    public void givenExistingUser_whenDelete_thenReturnNoContentResponse() {
        doReturn(new User())
                .when(userService)
                .find(1L);

        final ResponseEntity<UserDTO> responseEntity = userController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(userService, times(1)).delete(1L);
    }
}
