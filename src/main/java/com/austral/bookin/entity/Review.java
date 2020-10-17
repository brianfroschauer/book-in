package com.austral.bookin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "stars", nullable = false)
    private int stars;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @PrePersist
    public void setCreatedAt() {
        createdAt = new Date();
    }

    public Review(int stars, String comment, User user, Book book) {
        this.stars = stars;
        this.comment = comment;
        this.user = user;
        this.book = book;
    }
}