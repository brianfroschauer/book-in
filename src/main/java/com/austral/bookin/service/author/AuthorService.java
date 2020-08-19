package com.austral.bookin.service.author;

import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface AuthorService {

    /**
     * Find all authors.
     *
     * @return all authors or an empty list if there are no users.
     */
    List<Author> find(Specification<Author> specification);

    /**
     * Find the author with the provided id.
     *
     * @param id of the author to be found.
     * @return the author found.
     */
    Author find(Long id);


    /**
     * Persist the provided {@param author}.
     *
     * @param author to be persisted.
     * @return the persisted author with.
     */
    Author save(Author author);

    /**
     * Update the provided {@param author}.
     *
     * @param id of the author to be updated.
     * @param author to be updated.
     * @return the updated author.
     */
    Author update(Long id, Author author);

    /**
     * Delete the provided author.
     *
     * @param id of the author to be found.
     */
    void delete(Long id);

    /**
     * Delete the provided author.
     *
     * @param author of the author to be found.
     */
    void delete(Author author);

    /**
     * Check the provided date is before the actual date.
     *
     * @param date of the birth of author to be saved.
     */
    boolean checkDate(Date date);
}
