package com.austral.bookin.service.review;

import com.austral.bookin.entity.Review;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.ReviewRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
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
        return repository.save(review);
    }

    @Override
    public Review update(Long id, Review review) {
        return null;
    }

    @Override
    public void delete(Long id) {}
}