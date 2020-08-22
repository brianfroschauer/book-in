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
    Role findByAuthority(String authority);

    /**
     * Persist the provided {@param role}.
     *
     * @param role to be persisted.
     * @return the persisted role.
     */
    Role create(Role role);

    /**
     * Add a new role to the specified user.
     *
     * @param userId id of the user to update role.
     */
    void updateAuthority(Long userId);
}