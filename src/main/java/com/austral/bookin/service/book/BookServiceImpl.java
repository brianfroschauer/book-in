package com.austral.bookin.service.book;

import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.service.author.AuthorService;
import com.austral.bookin.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Book save(Book book, MultipartFile file) {
        if (file != null) book.setPhoto(FileHandler.getBytes(file));
        return repository.save(book);
    }

    @Override
    public Book update(Long id, Book book, MultipartFile file) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }
}