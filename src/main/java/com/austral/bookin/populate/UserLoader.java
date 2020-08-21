package com.austral.bookin.populate;

import com.austral.bookin.entity.Role;
import com.austral.bookin.entity.User;
import com.austral.bookin.repository.RoleRepository;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserLoader implements CommandLineRunner, Ordered {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserLoader(UserRepository userRepository,
                      RoleService roleService,
                      RoleRepository roleRepository,
                      PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {


        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_VERIFIED_USER");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        Role serviceRoleFetch = roleService.findByAuthority("ROLE_ADMIN");

        User admin = new User("Pedro","Garde","pedro@hotmail.com", encoder.encode("mightypassword"),"M");
        admin.setRoles(Collections.singleton(serviceRoleFetch));
        userRepository.save(admin);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
