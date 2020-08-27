package com.austral.bookin.repository;

import com.austral.bookin.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = "select * from book b where b.id in (select ba.book_id from book_author ba where ba.author_id = ?1)", nativeQuery = true)
    List<Book> findAllByAuthor(Long id);
}