package com.austral.bookin.service.user;

import com.austral.bookin.entity.Token;
import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.InvalidOldPasswordException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.entity.User;
import com.austral.bookin.util.FileHandler;
import com.austral.bookin.util.MailStrategy;
import com.austral.bookin.util.SendMailHandler;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final VelocityEngine velocityEngine;

    @Value("${url}")
    private String basicUrl;

    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder, VelocityEngine velocityEngine) {
        this.repository = repository;
        this.encoder = encoder;
        this.velocityEngine = velocityEngine;
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
    public User setPassword(Long userId, String password) {
        User user = find(userId);
        user.setPassword(encoder.encode(password));
        return repository.save(user);
    }

    @Override
    public User updatePassword(String oldPassword, String password, User user) {
        if (checkValidOldPassword(oldPassword, user))
            user.setPassword(encoder.encode(password));
        else
             throw new InvalidOldPasswordException();
        return repository.save(user);
    }

    @Override
    public void sendMail(MailStrategy strategy, User user, String... token) {
        Session session = SendMailHandler.setProperties();
        if (strategy == MailStrategy.REGISTER) {
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String[] values = {firstName, lastName};
            SendMailHandler.sendMail(velocityEngine, session, user, "Bienvenido a BookIn", "assets/templates/welcome.html", MailStrategy.REGISTER, values);
        } else {
            String[] url = {basicUrl + token[0]};
            SendMailHandler.sendMail(velocityEngine, session, user, "Recuperá tu contraseña", "assets/templates/recover.html", MailStrategy.RECOVER, url);
        }
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

    private boolean checkValidOldPassword(String old, User user) {
        return encoder.matches(old, user.getPassword());
    }
}
