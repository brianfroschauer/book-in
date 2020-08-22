package com.austral.bookin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "birthday")
    private Date birthday;

    @Lob
    @Column(name = "photo", columnDefinition="longblob")
    private byte[] photo;
}