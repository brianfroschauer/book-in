package com.austral.bookin.service.authorization;

import com.austral.bookin.entity.Role;
import com.austral.bookin.entity.User;
import com.austral.bookin.repository.RoleRepository;
import com.austral.bookin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthorizationServiceImpl(UserRepository userRepository,
                                    RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRoleAdmin(Long id) {
        final User user = userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User, " + id + ", is not found"));

        final Role role = roleRepository
                .findByAuthority("ROLE_ADMIN")
                .orElseThrow(() -> new EntityNotFoundException("Role, ROLE_ADMIN, is not found"));

        final Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }
}