package com.austral.bookin.repository;

import com.austral.bookin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by {@param username}.
     *
     * @param email of the user to be found.
     * @return an optional user.
     */
    Optional<User> findByEmail(String email);
}
