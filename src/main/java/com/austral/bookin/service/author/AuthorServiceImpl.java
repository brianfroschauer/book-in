package com.austral.bookin.service.author;

import com.austral.bookin.entity.Author;
import com.austral.bookin.exception.InternalServerException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Author save(Author author, MultipartFile file) throws IOException {
        if (file != null) author.setPhoto(file.getBytes());
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
                    if (file != null) old.setPhoto(getBytes(file));
                    return repository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new InternalServerException();
        }
    }
}