package com.austral.bookin.service.role;

import com.austral.bookin.entity.Role;
import com.austral.bookin.entity.User;
import com.austral.bookin.repository.RoleRepository;
import com.austral.bookin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Role findByAuthority(String authority) {
        return roleRepository
                .findByAuthority(authority)
                .orElseThrow(() -> new EntityNotFoundException("Role, " + authority + ", is not found"));
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void updateAuthority(Long userId) {
        final User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User, " + userId + ", is not found"));

        final Optional<Role> optionalRole = roleRepository.findByAuthority("ROLE_ADMIN");

        optionalRole.ifPresent(role -> {
            final Set<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        });
    }
}

