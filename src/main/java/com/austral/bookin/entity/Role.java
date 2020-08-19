package com.austral.bookin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity(name = "role")
public class Role implements GrantedAuthority { //ROLES IMPLEMENT AUTHS.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "authority",unique = true)
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }
}