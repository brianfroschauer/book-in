package com.austral.bookin.service.authorization;

import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {

    /**
     * Add the administration authority to the user with provided id.
     *
     * @param id of the user to be modified.
     */
    void addRoleAdmin(Long id);
}