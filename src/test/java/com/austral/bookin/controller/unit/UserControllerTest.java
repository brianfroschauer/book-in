package com.austral.bookin.controller.unit;

import com.austral.bookin.controller.UserController;
import com.austral.bookin.dto.user.UpdateUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.NotFoundException;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserSpecification userSpecification;

    @MockBean
    private UserService userService;

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
    @DisplayName("Given existing user, when update, then return Ok response")
    public void givenExistingUser_whenUpdate_thenReturnOkResponse() {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("firstName");
        updateUserDTO.setLastName("lastName");

        doReturn(new User())
                .when(userService)
                .update(eq(1L), any(User.class));

        final ResponseEntity<UserDTO> responseEntity = userController.update(1L, updateUserDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    @DisplayName("Given non existent user, when update, then throw Not Found exception")
    public void givenNonExistentUser_whenUpdate_thenReturnThrowNotFoundException() {
        final UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("firstName");
        updateUserDTO.setLastName("lastName");

        doThrow(NotFoundException.class)
                .when(userService)
                .update(eq(1L), any(User.class));

        assertThrows(NotFoundException.class, () -> userController.update(1L, updateUserDTO));
        verify(userService, times(1)).update(eq(1L), any(User.class));
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
