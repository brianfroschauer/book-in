package com.austral.bookin.service.user;

import com.austral.bookin.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface UserService {

    /**
     * Find all users.
     *
     * @return all users or an empty list if there are no users.
     */
    List<User> find(Specification<User> specification);

    /**
     * Find the user with the provided id.
     *
     * @param id of the user to be found.
     * @return the user found.
     */
    User find(Long id);

    /**
     * Find the user with the provided {@param email}.
     *
     * @param email of the user to be found.
     * @return the user found.
     */
    User find(String email);

    /**
     * Find the authenticated user.
     *
     * @param principal the authenticated user.
     * @return the user with provided token.
     */
    User find(Principal principal);

    /**
     * Update password of given {@param user} with given {@param password} if {@param oldPassword} checks with saved.
     *
     * @param oldPassword to be checked.
     * @param password to be updated.
     * @param user to be updated.
     * @return User updated.
     */
    User updatePassword(String oldPassword, String password, User user);

    /**
     * Persist the provided {@param user}.
     *
     * @param user to be persisted.
     * @return the persisted user.
     */
    User save(User user);

    /**
     * Update the provided {@param user}.
     *
     * @param id of the user to be updated.
     * @param user to be updated.
     * @param file to be updated.
     * @return the updated user.
     */
    User update(Long id, User user, MultipartFile file);

    /**
     * Delete the provided user.
     *
     * @param id of the user to be found.
     */
    void delete(Long id);
}
