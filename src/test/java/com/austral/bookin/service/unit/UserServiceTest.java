package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Token;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.InvalidOldPasswordException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.TokenRepository;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.service.user.UserService;
import com.austral.bookin.specification.UserSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserSpecification userSpecification;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        assertNotNull(userService);
    }

    @Test
    @DisplayName("Given user list, when find all, then return users")
    public void givenUserList_whenFindAll_thenReturnUsers() {
        doReturn(Arrays.asList(new User(), new User()))
                .when(userRepository)
                .findAll(userSpecification);

        final List<User> users = userService.find(userSpecification);

        verify(userRepository).findAll(userSpecification);
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Given empty list, when find all, then return empty list")
    public void givenEmptyList_whenFindAll_ThenReturnEmptyList() {
        doReturn(Collections.emptyList())
                .when(userRepository)
                .findAll(userSpecification);

        final List<User> users = userService.find(userSpecification);

        assertTrue(users.isEmpty());
        verify(userRepository).findAll(userSpecification);
    }

    @Test
    @DisplayName("Given present optional user, when find by id, then return user")
    public void givenPresentOptionalUser_whenFindById_ThenReturnUser() {
        doReturn(Optional.of(new User()))
                .when(userRepository)
                .findById(1L);

        final User user = userService.find(1L);

        assertNotNull(user);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Given empty optional user, when find by id, then throw not found exception")
    public void givenEmptyOptionalUser_whenFindById_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findById(1L);

        assertThrows(NotFoundException.class, () -> userService.find(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Given present optional user, when find by email, then return user")
    public void givenPresentOptionalUser_whenFindByEmail_ThenReturnUser() {
        doReturn(Optional.of(new User()))
                .when(userRepository)
                .findByEmail("user@gmail.com");

        final User user = userService.find("user@gmail.com");

        assertNotNull(user);
        verify(userRepository).findByEmail("user@gmail.com");
    }

    @Test
    @DisplayName("Given empty optional user, when find by email, then throw not found exception")
    public void givenEmptyOptionalUser_whenFindByEmail_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findByEmail("user@gmail.com");

        assertThrows(NotFoundException.class, () -> userService.find("user@gmail.com"));
        verify(userRepository).findByEmail("user@gmail.com");
    }

    @Test
    @DisplayName("Change password of given user, given right old password")
    public void changePasswordOfUser_givenRightOldPassword() {
        final PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("password123"), "F", new HashSet<>(), new byte[4], new ArrayList<>());
        User user2 = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("hola1234"), "F", new HashSet<>(), new byte[4], new ArrayList<>());

        Mockito.doReturn(user2)
                .when(userRepository)
                .save(user);

        final User result = userService.updatePassword("password123", "hola1234", user);

        assertEquals(user2, result);
    }

    @Test
    @DisplayName("Change password of given user")
    public void changePasswordOfUser() {
        final PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("password123"), "F", new HashSet<>(), new byte[4], new ArrayList<>());
        User user2 = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("hola1234"), "F", new HashSet<>(), new byte[4], new ArrayList<>());

        Mockito.doReturn(Optional.of(user))
                .when(userRepository)
                .findById(1L);

        Mockito.doReturn(user2)
                .when(userRepository)
                .save(user);

        final User result = userService.setPassword(1L, "hola1234");

        assertEquals(user2, result);
    }

    @Test
    @DisplayName("Change password of given user, given wrong old password")
    public void changePasswordOfUser_givenWrongOldPassword() {
        final PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("password123"), "F", new HashSet<>(), new byte[4], new ArrayList<>());

        assertThrows(InvalidOldPasswordException.class, () -> userService.updatePassword("password1234", "hola1234", user));
    }

    @Test
    @DisplayName("Send mail without problems")
    public void sendMailWithoutProblems() {
        final PasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User(1L, "Katia", "Cammisa", "katia@hotmail.com", encoder.encode("password123"), "F", new HashSet<>(), new byte[4], new ArrayList<>());
        Token token = new Token(2L, "asd12f", user, new Date(121, Calendar.NOVEMBER, 13));

        userService.sendMail(token);
    }

    @Test
    @DisplayName("Given user, when save, then return user")
    public void givenUser_whenSave_thenReturnUser() {
        doReturn(new User())
                .when(userRepository)
                .save(any(User.class));

        final User user = userService.save(new User());

        assertNotNull(user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Given present optional user, when update, then update user")
    public void givenPresentOptionalUser_whenUpdate_thenUpdateUser() {
        doReturn(Optional.of(new User()))
                .when(userRepository)
                .findById(1L);

        doReturn(new User())
                .when(userRepository)
                .save(any(User.class));

        final User user = userService.update(1L, new User(), null);

        assertNotNull(user);
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Given present optional user, when update, then throw not found exception")
    public void givenEmptyOptionalUser_whenUpdate_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findById(1L);

        assertThrows(NotFoundException.class, () -> userService.update(1L, new User(), null));
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Given present optional user, when delete, then delete user")
    public void givenPresentOptionalUser_whenDelete_thenDeleteUser() {
        doReturn(Optional.of(new User()))
                .when(userRepository)
                .findById(1L);

        userService.delete(1L);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    @DisplayName("Given empty optional user, when delete, then throw not found exception")
    public void givenEmptyOptionalUser_whenDelete_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findById(1L);

        assertThrows(NotFoundException.class, () -> userService.delete(1L));
        verify(userRepository).findById(1L);
        verify(userRepository, never()).delete(any(User.class));
    }
}
