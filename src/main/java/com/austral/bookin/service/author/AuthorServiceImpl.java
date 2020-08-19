package com.austral.bookin.service.author;

import com.austral.bookin.entity.Author;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository repository;

    @Override
    public List<Author> find(Specification<Author> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Author find(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Author save(Author author, MultipartFile file) throws IOException {
        author.setPhoto(file.getBytes());
        return repository.save(author);
    }

    @Override
    public Author update(Long id, Author author) {
        return repository
                .findById(id)
                .map(old -> {
                    old.setFirstName(author.getFirstName());
                    old.setLastName(author.getLastName());
                    if (author.getNationality() != null) {
                        old.setNationality(author.getNationality());
                    }
                    if (author.getBirthday() != null) {
                        old.setBirthday(author.getBirthday());
                    }
                    if (author.getPhoto() != null) {
                        old.setPhoto(author.getPhoto());
                    }
                    return repository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }

    @Override
    public void delete(Author author) {
        repository.delete(author);
    }
}