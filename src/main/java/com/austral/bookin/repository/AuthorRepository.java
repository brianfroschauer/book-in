package com.austral.bookin.repository;

import com.austral.bookin.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {

    @Query(value = "select * from author a where a.id in (select ba.author_id from book_author ba where ba.book_id = ?1)", nativeQuery = true)
    List<Author> findAllByBook(Long id);
}