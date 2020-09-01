package com.austral.bookin.service.book;

import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.util.FileHandler;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> find(Specification<Book> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Book find(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Book> findByAuthor(Long id) {
        return repository.findAllByAuthor(id);
    }

    @Override
    public double getStars(Long id) {
        return round(repository.getStars(id));
    }

    @Override
    public Book save(Book book, MultipartFile file) {
        repository
                .findBookByTitle(book.getTitle())
                .ifPresent(found -> { throw new AlreadyExistsException(); });

        if (file != null) book.setPhoto(FileHandler.getBytes(file));
        return repository.save(book);
    }

    @Override
    public Book update(Long id, Book book, MultipartFile file) {
        return repository
                .findById(id)
                .map(old -> {
                    if (book.getTitle() != null) old.setTitle(book.getTitle());
                    if (book.getGenre() != null) old.setGenre(book.getGenre());
                    if (book.getLanguage() != null) old.setLanguage(book.getLanguage());
                    if (book.getDate() != null) old.setDate(book.getDate());
                    if (!book.getAuthors().isEmpty()) old.setAuthors(book.getAuthors());
                    if (file != null) old.setPhoto(FileHandler.getBytes(file));
                    return repository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private double round(double stars) {
        if (stars == 0) return 0;
        if(stars - Math.floor(stars) < 0.5) {
            return (Math.floor(stars) + 0.5);
        } else {
            return Math.ceil(stars);
        }
    }
}