package com.austral.bookin.service.user;

import com.austral.bookin.exception.InternalServerException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public List<User> find(Specification<User> specification) {
        return repository
                .findAll(specification);
    }

    @Override
    public User find(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public User find(String email) {
        return repository
                .findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public User findMe() {
        final Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        final String principal = (String) authentication.getPrincipal();
        return repository
                .findByEmail(principal)
                .orElseThrow(() -> new EntityNotFoundException("User, " + principal + ", is not found"));
    }

    @Override
    public User save(User user) {
        return repository
                .save(user);
    }

    @Override
    public User update(Long id, User user, MultipartFile file) {
        return repository
                .findById(id)
                .map(old -> {
                    old.setFirstName(user.getFirstName());
                    old.setLastName(user.getLastName());
                    if (user.getPassword() != null) old.setPassword(encode(user.getPassword()));
                    if (user.getEmail() != null) old.setEmail(user.getEmail());
                    if (user.getGender() != null) old.setGender(user.getGender());
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

    private String encode(String password) {
        return encoder.encode(password);
    }
}