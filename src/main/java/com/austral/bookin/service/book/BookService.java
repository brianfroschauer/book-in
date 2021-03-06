package com.austral.bookin.service.book;

import com.austral.bookin.entity.Book;
import com.austral.bookin.util.Strategy;
import org.springframework.data.domain.Pageable;
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
     * Find all books with the given specification and a pageable.
     *
     * @return all books or an empty list if there are no books.
     */
    List<Book> findAll(Specification<Book> specification, Pageable pageable);

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
    Book save(Book book, List<Long> authors, MultipartFile file);

    /**
     * Updates the provided book {@param id}, {@param stars}, {@param strategy} and {@param oldStars}.
     *
     * @param id of the book to be updated.
     * @param stars updated in book.
     * @param strategy to be implemented.
     * @param oldStars to change in case of updating review.
     * @return the updated book.
     */
    Book calculateStars(long id, int stars, Strategy strategy, int... oldStars);
  
    /**
     * Sorts all books adn returns a list with the top {@param size} ranked.
     *
     * @param size of the list to be retrieved.
     * @return the sorted list of books.
     */
    List<Book> sortByStars(int size);
  
    /**
     * Gets list of books of given {@param genre} and {@param size}.
     *
     * @param genre to be sorted.
     * @param size of the list expected.
     * @return the sorted list of books.
     */
    List<Book> sortByGenre(String genre, int size);

    /**
     * Update the provided {@param book} and {@param file}.
     *
     * @param id of the book to be updated.
     * @param book to be updated.
     * @param file to be updated.
     * @return the updated book.
     */
    Book update(Long id, Book book, List<Long> authors, MultipartFile file);

    /**
     * Delete the provided author.
     *
     * @param id of the book to be deleted.
     */
    void delete(Long id);
}
