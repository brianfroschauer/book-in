package com.austral.bookin.service.review;

import com.austral.bookin.entity.Book;
import com.austral.bookin.entity.Review;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.ReviewRepository;
import com.austral.bookin.service.book.BookService;
import com.austral.bookin.util.Strategy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;
    private final BookService bookService;

    public ReviewServiceImpl(ReviewRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public List<Review> find(Specification<Review> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Review find(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Review> findByBook(Long id) {
        return repository.findByBook(id);
    }

    @Override
    public List<Review> findByUser(Long id) {
        return repository.findByUser(id);
    }

    @Override
    public Review save(Review review) {
        review.setBook(bookService.calculateStars(review.getBook().getId(), review.getStars(), Strategy.CREATE));
        return repository.save(review);
    }

    @Override
    public Review update(Long id, Review review) {
        return repository
                .findById(id)
                .map(old -> {
                    Book updatedBook = bookService.calculateStars(old.getBook().getId(), review.getStars(), Strategy.UPDATE, old.getStars());
                    if (review.getStars() != 0) old.setStars(review.getStars());
                    if (review.getComment() != null) old.setComment(review.getComment());
                    old.setBook(updatedBook);
                    return repository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        Review review = find(id);
        bookService.calculateStars(review.getBook().getId(), review.getStars(), Strategy.DELETE);
        repository.delete(review);
    }
}