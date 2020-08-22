package com.austral.bookin.service.role;

import com.austral.bookin.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    /**
     * Find a role with the provided authority.
     *
     * @param authority of the role to be found.
     * @return the role with the provided authority.
     */
    Role find(String authority);

    /**
     * Persist the provided {@param role}.
     *
     * @param role to be persisted.
     */
    void create(Role role);

    /**
     * Make admin the user with the provided {@param userId}.
     *
     * @param userId id of the user to make admin.
     */
    void makeAdmin(Long userId);
}