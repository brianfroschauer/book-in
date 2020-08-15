package com.austral.bookin.service.unit;

import com.austral.bookin.entity.User;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

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
                .findAll();

        final List<User> users = userService.find();

        verify(userRepository).findAll();
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Given empty list, when find all, then return empty list")
    public void givenEmptyList_whenFindAll_ThenReturnEmptyList() {
        doReturn(Collections.emptyList())
                .when(userRepository)
                .findAll();

        final List<User> users = userService.find();

        assertTrue(users.isEmpty());
        verify(userRepository).findAll();
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
                .findByEmail("email123@email123.com");

        final User user = userService.find("email123@email123.com");

        assertNotNull(user);
        verify(userRepository).findByEmail("email123@email123.com");
    }

    @Test
    @DisplayName("Given empty optional user, when find by email, then throw not found exception")
    public void givenEmptyOptionalUser_whenFindByEmail_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(userRepository)
                .findByEmail("email12123@email12.com");

        assertThrows(NotFoundException.class, () -> userService.find("email12123@email12.com"));
        verify(userRepository).findByEmail("email12123@email12.com");
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

        final User user = userService.update(1L, new User());

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

        assertThrows(NotFoundException.class, () -> userService.update(1L, new User()));
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
