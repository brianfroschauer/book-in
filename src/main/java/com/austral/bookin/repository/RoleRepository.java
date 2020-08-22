package com.austral.bookin.repository;

import com.austral.bookin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by {@param authority}.
     *
     * @param authority of the roles to be found.
     * @return an optional role.
     */
    Optional<Role> findByAuthority(String authority);
}
