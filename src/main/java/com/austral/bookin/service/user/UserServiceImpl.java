package com.austral.bookin.service.user;

import com.austral.bookin.entity.Token;
import com.austral.bookin.exception.AlreadyExistsException;
import com.austral.bookin.exception.InvalidOldPasswordException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.UserRepository;
import com.austral.bookin.entity.User;
import com.austral.bookin.util.FileHandler;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Properties;

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
    public void sendMail(Token token) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "bookin.team.austral");
        props.put("mail.smtp.clave", "bookin123");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        String url = "http://localhost:8080/users/resetPassword?token=" + token.getToken();

        String htmlMessage = constructMessage(url);

        try {
            message.setFrom(new InternetAddress("bookin.team.austral"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(token.getUser().getEmail()));
            message.setSubject("Recuperá tu contraseña");
            message.setContent(htmlMessage, "text/html; charset=utf-8");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", "bookin.team.austral", "bookin123");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
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

    private String constructMessage(String url) {
        String header = "<h2><strong> Recuperá tu contraseña </strong></h2>";
        String link = "<a href=\"" + url +"\">click acá</a>";
        String body = "Haga " + link + " para poder recuperar su contraseña.<br><br>Si Ud. no solicitó la recuperación de su contraseña o nunca se registró en Book <i>In</i> puede ignorar este mail.<br><br>Saludos,<br>Book <i>In</i>";
        return header + "\n\n\n" + body;
    }
}
