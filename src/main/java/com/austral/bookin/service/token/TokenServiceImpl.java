package com.austral.bookin.service.token;

import com.austral.bookin.entity.Token;
import com.austral.bookin.entity.User;
import com.austral.bookin.exception.ExpiredTokenException;
import com.austral.bookin.exception.NotFoundException;
import com.austral.bookin.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token find(Long id) {
        return tokenRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Token find(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public Token find(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public Token createPasswordResetToken(User user) {
        Token newToken = new Token();
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setUser(user);
        newToken.setExpiryDate(calculateExpiration());
        return tokenRepository.save(newToken);
    }

    @Override
    public void validateToken(Token token) {
        if (isExpired(token)) throw new ExpiredTokenException();
        else delete(token);
    }

    @Override
    public void delete(Token token) {
        tokenRepository.delete(token);
    }

    private boolean isExpired(Token token) {
        Date now = new Date();
        return now.after(token.getExpiryDate());
    }

    private Date calculateExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + (2*60*60*1000));
        return expiration;
    }
}
