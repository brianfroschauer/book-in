package com.austral.bookin.service.role;

import com.austral.bookin.entity.Role;
import com.austral.bookin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role find(String authority) {
        return roleRepository
                .findByAuthority(authority)
                .orElseThrow(() -> new EntityNotFoundException("Role, " + authority + ", is not found"));
    }

    @Override
    public void create(Role role) {
        roleRepository.save(role);
    }
}

