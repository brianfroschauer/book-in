package com.austral.bookin.service.author;

import com.austral.bookin.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthorService {

    /**
     * Find all authors.
     *
     * @return all authors or an empty list if there are no authors.
     */
    List<Author> find(Specification<Author> specification);

    /**
     * Find all authors with the given specification and a pageable.
     *
     * @return all authors or an empty list if there are no authors.
     */
    List<Author> findAll(Specification<Author> specification, Pageable pageable);

    /**
     * Find the author with the provided id.
     *
     * @param id of the author to be found.
     * @return the author found.
     */
    Author find(Long id);

    /**
     * Persist the provided {@param author} and {@param file}.
     *
     * @param author to be persisted.
     * @param file to be persisted.
     * @return the persisted author with.
     */
    Author save(Author author, MultipartFile file);

    /**
     * Update the provided {@param author} and {@param file}.
     *
     * @param id of the author to be updated.
     * @param author to be updated.
     * @param file to be updated.
     * @return the updated author.
     */
    Author update(Long id, Author author, MultipartFile file);

    /**
     * Delete the provided author.
     *
     * @param id of the author to be found.
     */
    void delete(Long id);
}