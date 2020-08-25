package com.austral.bookin.service.book;

import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.InternalServerException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.BookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
        List<Book> books = repository.findAll();
        List<Book> booksByAuthor = new ArrayList<>();
        for(Book book : books) {
            for (Author author : book.getAuthors()) {
                if (author.getId().equals(id)) {
                    booksByAuthor.add(book);
                }
            }
        }
        return booksByAuthor;
    }

    @Override
    public Book save(Book book, MultipartFile file) {
        if (file != null) book.setPhoto(getBytes(file));
        return repository.save(book);
    }

    @Override
    public Book update(Long id, Book book, MultipartFile file) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new InternalServerException();
        }
    }
}