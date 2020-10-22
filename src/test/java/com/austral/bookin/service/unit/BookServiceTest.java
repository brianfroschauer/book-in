package com.austral.bookin.service.unit;

import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.repository.ReviewRepository;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.specification.BookSpecification;
import com.austral.bookin.util.Strategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookSpecification bookSpecification;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void contextLoads() {
        assertNotNull(bookService);
    }

    @Test
    @DisplayName("Given books list, when find all, then return books")
    public void givenBoooks_WhenFindAll_ReturnBooks() {
        doReturn(Arrays.asList(new Book(), new Book()))
                .when(bookRepository)
                .findAll(bookSpecification);

        final List<Book> books = bookService.find(bookSpecification);

        verify(bookRepository).findAll(bookSpecification);
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Given empty list, when find all, then return empty list")
    public void givenEmptyList_whenFindAll_ThenReturnEmptyList() {
        doReturn(Collections.emptyList())
                .when(bookRepository)
                .findAll(bookSpecification);

        final List<Book> books = bookService.find(bookSpecification);

        assertTrue(books.isEmpty());
        verify(bookRepository).findAll(bookSpecification);
    }

    @Test
    @DisplayName("Given present optional book, when find by id, then return book")
    public void givenPresentOptionalBook_whenFindById_ThenReturnBook() {
        doReturn(Optional.of(new Book()))
                .when(bookRepository)
                .findById(4L);

        final Book book = bookService.find(4L);

        assertNotNull(book);
        verify(bookRepository).findById(4L);
    }

    @Test
    @DisplayName("Given empty optional book, when find by id, then throw not found exception")
    public void givenEmptyOptionalBook_whenFindById_ThenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> bookService.find(4L));
        verify(bookRepository).findById(4L);
    }

    @Test
    @DisplayName("Given book, when save, then return book")
    public void givenBook_whenSave_thenReturnBook() {
        doReturn(new Book())
                .when(bookRepository)
                .save(any(Book.class));

        final Book book = bookService.save(new Book(), null);

        assertNotNull(book);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Given present optional book, when update, then update book")
    public void givenPresentOptionalBook_whenUpdate_thenUpdateBook() {
        doReturn(Optional.of(new Book()))
                .when(bookRepository)
                .findById(4L);

        doReturn(new Book())
                .when(bookRepository)
                .save(any(Book.class));

        final Book book = bookService.update(4L, new Book(), null);

        assertNotNull(book);
        verify(bookRepository).findById(4L);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Given present optional book, when update, then throw not found exception")
    public void givenEmptyOptionalBook_whenUpdate_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> bookService.update(4L, new Book(), null));
        verify(bookRepository).findById(4L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Given present optional book, when delete, then delete book")
    public void givenPresentOptionalBook_whenDelete_thenDeleteBook() {
        doReturn(Optional.of(new Book()))
                .when(bookRepository)
                .findById(4L);

        bookService.delete(4L);

        verify(bookRepository).delete(any(Book.class));
    }

    @Test
    @DisplayName("Given empty optional book, when delete, then throw not found exception")
    public void givenEmptyOptionalBook_whenDelete_thenThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(bookRepository)
                .findById(4L);

        assertThrows(NotFoundException.class, () -> bookService.delete(4L));
        verify(bookRepository).findById(4L);
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    @DisplayName("Given new review, update stars")
    public void givenNewReview_updateStars() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        User user = new User("Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>());
        User user2 = new User("Lalo", "Cammisa", "lalo@hotmail.com", "password123", "M", new HashSet<>());

        Review review = new Review(5, "Muy bueno", user, book);
        Review review2 = new Review(3, "Meh", user2, book);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review2);

        doReturn(Optional.of(book))
                .when(bookRepository)
                .findById(1L);

        doReturn(book)
                .when(bookRepository)
                .save(book);

        doReturn(reviews)
                .when(reviewRepository)
                .findByBook(1L);

        Book new_book = bookService.calculateStars(1L, 4, Strategy.CREATE);

        assert new_book.getStars() == 4;
    }

    @Test
    @DisplayName("Updating review then update stars")
    public void updatingReview_updateStars() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        User user = new User("Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>());
        User user2 = new User("Lalo", "Cammisa", "lalo@hotmail.com", "password123", "M", new HashSet<>());

        Review review = new Review(5, "Muy bueno", user, book);
        Review review2 = new Review(3, "Meh", user2, book);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review2);

        doReturn(Optional.of(book))
                .when(bookRepository)
                .findById(1L);

        doReturn(book)
                .when(bookRepository)
                .save(book);

        doReturn(reviews)
                .when(reviewRepository)
                .findByBook(1L);

        Book new_book = bookService.calculateStars(1L, 3, Strategy.UPDATE, 5);

        assert new_book.getStars() == 3;
    }

    @Test
    @DisplayName("Deleting review then update stars")
    public void deletingReview_updateStars() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        User user = new User("Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>());
        User user2 = new User("Lalo", "Cammisa", "lalo@hotmail.com", "password123", "M", new HashSet<>());

        Review review = new Review(5, "Muy bueno", user, book);
        Review review2 = new Review(3, "Meh", user2, book);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review2);

        doReturn(Optional.of(book))
                .when(bookRepository)
                .findById(1L);

        doReturn(book)
                .when(bookRepository)
                .save(book);

        doReturn(reviews)
                .when(reviewRepository)
                .findByBook(1L);

        Book new_book = bookService.calculateStars(1L, 3, Strategy.DELETE);

        assert new_book.getStars() == 5;
    }

    @Test
    @DisplayName("Deleting last review of book then update stars")
    public void deletingLastReview_updateStars() {
        Book book = new Book(1L, "title", "Aventura", "en", new Date(), new ArrayList<>());
        User user = new User("Katia", "Cammisa", "katia@hotmail.com", "password123", "F", new HashSet<>());

        Review review = new Review(5, "Muy bueno", user, book);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        doReturn(Optional.of(book))
                .when(bookRepository)
                .findById(1L);

        doReturn(book)
                .when(bookRepository)
                .save(book);

        doReturn(reviews)
                .when(reviewRepository)
                .findByBook(1L);

        Book new_book = bookService.calculateStars(1L, 5, Strategy.DELETE);

        assert new_book.getStars() == 0.0;
    }
}