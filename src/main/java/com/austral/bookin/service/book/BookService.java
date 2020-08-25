package com.austral.bookin.service.book;

import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    /**
     * Find all books.
     *
     * @return all books or an empty list if there are no books.
     */
    List<Book> find(Specification<Book> specification);

    /**
     * Find the book with the provided id.
     *
     * @param id of the book to be found.
     * @return the book found.
     */
    Book find(Long id);

    /**
     * Find all the books from the author with the provided id.
     *
     * @param id of the author.
     * @return the books found.
     */

    List<Book> findByAuthor(Long id);

    /**
     * Persist the provided {@param book} and {@param file}.
     *
     * @param book to be persisted.
     * @param file to be persisted.
     * @return the persisted book.
     */
    Book save(Book book, MultipartFile file);

    /**
     * Update the provided {@param book} and {@param file}.
     *
     * @param id of the book to be updated.
     * @param book to be updated.
     * @param file to be updated.
     * @return the updated book.
     */
    Book update(Long id, Book book, MultipartFile file);

    /**
     * Delete the provided author.
     *
     * @param id of the book to be deleted.
     */
    void delete(Long id);
}