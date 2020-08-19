package com.austral.bookin.repository;

import com.austral.bookin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Find user by {@param email}.
     *
     * @param email of the user to be found.
     * @return an optional user.
     */
    Optional<User> findByEmail(String email);


}
