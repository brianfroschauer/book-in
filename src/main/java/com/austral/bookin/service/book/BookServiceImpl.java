package com.austral.bookin.service.book;

import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.repository.ReviewRepository;
import com.austral.bookin.util.FileHandler;
import com.austral.bookin.util.Strategy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Book> find(Specification<Book> specification) {
        return bookRepository.findAll(specification);
    }

    @Override
    public List<Book> findAll(Specification<Book> specification, Pageable pageable) {
        return bookRepository
                .findAll(specification, pageable)
                .toList();
    }

    @Override
    public Book find(Long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Book> findByAuthor(Long id) {
        return bookRepository.findAllByAuthor(id);
    }

    @Override
    public Book save(Book book, MultipartFile file) {
        bookRepository
                .findBookByTitle(book.getTitle())
                .ifPresent(found -> { throw new AlreadyExistsException(); });

        if (file != null) book.setPhoto(FileHandler.getBytes(file));
        return bookRepository.save(book);
    }

    @Override
    public Book calculateStars(long id, int stars, Strategy strategy, int... oldStars) {
        final Book book = find(id);
        final List<Review> reviews = reviewRepository.findByBook(book.getId());

        float newStars = (strategy == Strategy.CREATE ? ((float) reviews
                                    .stream()
                                    .map(Review::getStars)
                                    .reduce(0, Integer::sum) + stars) / (reviews.size() + 1)
                                :(strategy == Strategy.DELETE ? (reviews.size() > 1 ? (float) (reviews
                                    .stream()
                                    .map(Review::getStars)
                                    .reduce(0, Integer::sum) - stars) / (reviews.size() - 1) : 0)
                                : ((float) reviews
                                    .stream()
                                    .map(Review::getStars)
                                    .reduce(0, Integer::sum) + stars - oldStars[0]) / (reviews.size())));

        book.setStars(newStars);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> sortByStars(int size) {
        return bookRepository.sortByStars(size);
    }

    @Override
    public Book update(Long id, Book book, MultipartFile file) {
        return bookRepository
                .findById(id)
                .map(old -> {
                    old.setTitle(book.getTitle());
                    old.setGenre(book.getGenre());
                    old.setLanguage(book.getLanguage());
                    old.setDate(book.getDate());
                    old.setAuthors(book.getAuthors());
                    if (file != null) old.setPhoto(FileHandler.getBytes(file));
                    return bookRepository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        bookRepository.delete(find(id));
    }
}
