package com.austral.bookin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "date", nullable = false)
    private Date date;

    @Lob
    @Column(name = "photo", columnDefinition="longblob", nullable = false)
    private byte[] photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private List<Author> authors = new ArrayList<>();
}