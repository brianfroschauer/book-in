package com.austral.bookin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @Column(name = "photo", columnDefinition = "longblob")
    private byte[] photo;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;

    @PreRemove
    private void removeBooks() {
        books.forEach(book -> book.getAuthors().remove(this));
    }

    public Author(String firstName, String lastName, String nationality, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthday = birthday;
    }
}