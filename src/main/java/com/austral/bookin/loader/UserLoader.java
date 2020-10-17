package com.austral.bookin.loader;

import com.austral.bookin.entity.Role;
import com.austral.bookin.entity.User;
import com.austral.bookin.service.role.RoleService;
import com.austral.bookin.service.signup.SignupService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.*;

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

        final Set<Role> roles = new HashSet<>(Collections.singleton(roleService.find("ROLE_ADMIN")));

        final User user1 = new User(
                "Pedro",
                "Gardelliano",
                "pedro@hotmail.com",
                "password123",
                "M",
                roles);

        final User user2 = new User(
                "Katia",
                "Cammisa",
                "katia@hotmail.com",
                "password123",
                "F",
                roles);

        final User user3 = new User(
                "Matias",
                "Gayo",
                "matias@hotmail.com",
                "password123",
                "M",
                new HashSet<>());

        final User user4 = new User(
                "Juan",
                "Ricci",
                "juan@hotmail.com",
                "password123",
                "M",
                new HashSet<>());

        final User user5 = new User(
                "Brian",
                "Froschauer",
                "brian@hotmail.com",
                "password123",
                "M",
                new HashSet<>());

        final User user6 = new User(
                "Diego",
                "Baldassare",
                "diego@hotmail.com",
                "password123",
                "M",
                new HashSet<>());

        final User user7 = new User(
                "Matias",
                "Venditti",
                "matiasv@hotmail.com",
                "password123",
                "M",
                new HashSet<>());

        final User user8 = new User(
                "Bruno",
                "De Luca",
                "bruno@hotmail.com",
                "password123",
                "M",
                new HashSet<>());

        signupService.signup(user1);
        signupService.signup(user2);
        signupService.signup(user3);
        signupService.signup(user4);
        signupService.signup(user5);
        signupService.signup(user6);
        signupService.signup(user7);
        signupService.signup(user8);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
