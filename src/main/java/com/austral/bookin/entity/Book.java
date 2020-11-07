package com.austral.bookin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
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

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "date", nullable = false)
    private Date date;

    @Lob
    @Column(name = "photo", columnDefinition = "longblob", nullable = false)
    private byte[] photo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    @JsonManagedReference
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(name = "stars")
    private float stars;

    @PreRemove
    private void removeAuthors() {
        authors.forEach(author -> author.getBooks().remove(this));
    }

    public Book(String title, String genre, String language, Date date, List<Author> authors) {
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.date = date;
        this.authors = authors;
    }

    public Book(long id, String title, String genre, String language, Date date, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.date = date;
        this.authors = authors;
    }
}