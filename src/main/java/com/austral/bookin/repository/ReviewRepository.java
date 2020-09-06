package com.austral.bookin.repository;

import com.austral.bookin.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @Query(value = "select * from review r where r.book_id = ?1", nativeQuery = true)
    List<Review> findByBook(Long id);

    @Query(value = "select * from review r where r.user_id = ?1", nativeQuery = true)
    List<Review> findByUser(Long id);
}