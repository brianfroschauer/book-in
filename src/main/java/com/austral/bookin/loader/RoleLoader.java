package com.austral.bookin.loader;

import com.austral.bookin.entity.Role;
import com.austral.bookin.service.role.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class RoleLoader implements CommandLineRunner, Ordered {

    private final RoleService roleService;

    public RoleLoader(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        roleService.create(new Role("ROLE_ADMIN"));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}