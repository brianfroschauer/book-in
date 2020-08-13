package com.austral.bookin.service.user;

import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
    public User save(User user) {
        return repository
                .save(user);
    }

    @Override
    public User update(Long id, User user) {
        return repository
                .findById(id)
                .map(old -> {
                    old.setFirstName(user.getFirstName());
                    old.setLastName(user.getLastName());
                    return repository.save(user);
                })
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        repository.delete(find(id));
    }
}
