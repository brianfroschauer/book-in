package com.austral.bookin.util;

import com.austral.bookin.dto.user.LoginUserDTO;
import com.austral.bookin.dto.user.UserDTO;
import com.austral.bookin.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ObjectMapperImplTest {

    @Autowired
    private ObjectMapperImpl objectMapperImpl;

    @Test
    @DisplayName("Given user, when map to user DTO, then return user DTO")
    public void givenUser_whenMapToUserDTO_thenReturnUserDTO() {
        final User user = new User();
        user.setId(1L);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("user@gmail.com");
        user.setPassword("password");

        final UserDTO userDTO = objectMapperImpl.map(user, UserDTO.class);
        assertNotNull(userDTO);
        assertEquals("firstName", userDTO.getFirstName());
        assertEquals("lastName", userDTO.getLastName());
        assertEquals("user@gmail.com", userDTO.getEmail());
    }

    @Test
    @DisplayName("Given user, when map to user DTO, then return user DTO")
    public void givenLOGIN_whenMapToLoginDTO_thenReturnLoginDTO() {
        final User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("password");

        final LoginUserDTO loginUserDTO = objectMapperImpl.map(user, LoginUserDTO.class);
        assertNotNull(loginUserDTO);
        assertEquals("user@gmail.com", loginUserDTO.getEmail());
        assertEquals("password",loginUserDTO.getPassword());
    }

    @Test
    @DisplayName("Given user list, when map to user DTO, then return list of user DTO")
    public void givenUserList_whenMapToUserDTO_thenReturnUserListDTO() {
        final List<UserDTO> users = objectMapperImpl.map(Arrays.asList(new User(), new User()), UserDTO.class);
        assertNotNull(users);
        assertEquals(2, users.size());
    }
}
