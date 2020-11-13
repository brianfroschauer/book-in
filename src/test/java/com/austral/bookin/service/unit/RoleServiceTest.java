package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Role;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.RoleRepository;
import com.austral.bookin.service.role.RoleService;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @MockBean
    private VelocityEngine velocityEngine;

    @Test
    public void contextLoads() {
        assertNotNull(roleService);
    }

    @Test
    @DisplayName("Given role, save it")
    public void givenRole_SaveIr() {
        doReturn(new Role())
                .when(roleRepository)
                .save(any(Role.class));

        roleService.create(new Role("ADMIN"));

        verify(roleRepository).save(any(Role.class));
    }

    @Test
    @DisplayName("Given authority, find role and return it")
    public void GivenAuthority_FindRoleAndReturnIt() {
        doReturn(Optional.of(new Role("ADMIN")))
                .when(roleRepository)
                .findByAuthority("ADMIN");

        final Role role = roleService.find("ADMIN");

        assertNotNull(role);
        verify(roleRepository).findByAuthority("ADMIN");
    }

    @Test
    @DisplayName("Given invalid authority, throw Not Found Exception")
    public void GivenInvalidAuthority_ThrowNotFoundException() {
        doThrow(NotFoundException.class)
                .when(roleRepository)
                .findByAuthority("INVALID");

        assertThrows(NotFoundException.class, () -> roleService.find("INVALID"));
        verify(roleRepository).findByAuthority("INVALID");
    }
}
