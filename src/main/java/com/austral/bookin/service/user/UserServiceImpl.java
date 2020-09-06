package com.austral.bookin.service.user;

import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.entity.User;
import com.austral.bookin.util.FileHandler;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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
    public User find(Principal principal) {
        final String email = principal.getName();
        return repository
                .findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User, " + email + ", is not found"));
    }

    @Override
    public User save(User user) {
        repository
                .findByEmail(user.getEmail())
                .ifPresent(found -> { throw new AlreadyExistsException(); });

        return repository.save(user);
    }

    @Override
    public User update(Long id, User user, MultipartFile file) {
        return repository
                .findById(id)
                .map(old -> {
                    old.setFirstName(user.getFirstName());
                    old.setLastName(user.getLastName());
                    if (user.getGender() != null) old.setGender(user.getGender());
                    if (file != null) old.setPhoto(FileHandler.getBytes(file));
                    return repository.save(old);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }
}