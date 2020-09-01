package com.austral.bookin.service.review;

import com.austral.bookin.entity.Review;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ReviewService {

    /**
     * Find all reviews.
     *
     * @return all reviews or an empty list if there are no reviews.
     */
    List<Review> find(Specification<Review> specification);

    /**
     * Find the review with the provided id.
     *
     * @param id of the review to be found.
     * @return the review found.
     */
    Review find(Long id);

    /**
     * Find the reviews with the provided book id.
     *
     * @param id of the book.
     * @return the reviews found.
     */
    List<Review> findByBook(Long id);

    /**
     * Find the reviews with the provided user id.
     *
     * @param id of the user.
     * @return the reviews found.
     */
    List<Review> findByUser(Long id);

    /**
     * Persist the provided {@param review}
     *
     * @param review to be persisted.
     * @return the persisted review with.
     */
    Review save(Review review);

    /**
     * Update the provided {@param review}
     *
     * @param id of the review to be updated.
     * @param review to be updated.
     * @return the updated review.
     */
    Review update(Long id, Review review);

    /**
     * Delete the provided review.
     *
     * @param id of the review to be found.
     */
    void delete(Long id);
}