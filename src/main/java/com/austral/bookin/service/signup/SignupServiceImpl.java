package com.austral.bookin.service.signup;

import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.BadFormatException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.entity.User;
import com.austral.bookin.util.Patterns;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignupServiceImpl implements SignupService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public SignupServiceImpl(UserRepository repository,
                             PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public User signup(User user) {
        validate(user.getEmail());
        user.setPassword(encode(user.getPassword()));
        return repository.save(user);
    }

    /**
     * Throw an AlreadyExistsException if the provided {@param username} already exists.
     * @param email to be found.
     */
    private void validate(String email) {

        if (!email.matches(Patterns.PATTERN_EMAIL)) throw new BadFormatException();


        repository
                .findByEmail(email)
                .ifPresent(user -> { throw new AlreadyExistsException(); });
    }

    /**
     * Encode the provided {@param password}.
     *
     * @param password to by encoded.
     * @return the encoded password.
     */
    private String encode(String password) {
        return encoder.encode(password);
    }
}
