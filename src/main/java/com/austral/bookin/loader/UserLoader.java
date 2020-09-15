package com.austral.bookin.loader;

import com.austral.bookin.entity.Role;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.role.RoleService;
import com.austral.bookin.service.signup.SignupService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Profile("!test")
@Component
public class UserLoader implements CommandLineRunner, Ordered {

    private final SignupService signupService;
    private final RoleService roleService;

    public UserLoader(SignupService signupService,
                      RoleService roleService) {
        this.signupService = signupService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        final User user = new User(
                "Pedro",
                "Garde",
                "pedro@hotmail.com",
                "password123",
                "M");

        final Role role = roleService.find("ROLE_ADMIN");
        user.setRoles(Collections.singleton(role));
        signupService.signup(user);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
