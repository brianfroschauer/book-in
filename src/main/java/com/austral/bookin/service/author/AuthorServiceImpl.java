package com.austral.bookin.service.author;

import com.austral.bookin.entity.Author;
import com.austral.bookin.entity.Book;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.AuthorRepository;
import com.austral.bookin.repository.BookRepository;
import com.austral.bookin.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Author> find(Specification<Author> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Author find(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Author save(Author author, MultipartFile file) {
        if (file != null) author.setPhoto(FileHandler.getBytes(file));
        return repository.save(author);
    }

    @Override
    public Author update(Long id, Author author, MultipartFile file) {
        return repository
                .findById(id)
                .map(old -> {
                    old.setFirstName(author.getFirstName());
                    old.setLastName(author.getLastName());
                    if (author.getNationality() != null) old.setNationality(author.getNationality());
                    if (author.getBirthday() != null) old.setBirthday(author.getBirthday());
                    if (file != null) old.setPhoto(FileHandler.getBytes(file));
                    return repository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        List<Book> books = bookRepository.findAllByAuthor(id);
        for (Book b : books) {
            List<Author> authors = b.getAuthors();
            for (int i = 0; i < authors.size(); i++) {
                if (authors.get(i).getId().equals(id)) {
                    authors.remove(i);
                    break;
                }
            }
        }
        Author author = find(id);
        author.setBooks(null);
        repository.save(author);
        repository.delete(find(id));
    }
}