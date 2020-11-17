package com.austral.bookin.service.token;

import com.austral.bookin.entity.Token;
import com.austral.bookin.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {

    /**
     * Find the token with the provided id.
     *
     * @param id of the token to be found.
     * @return the token found.
     */
    Token find(Long id);

    /**
     * Find the token with the provided {@param token}.
     *
     * @param token of the token to be found.
     * @return the token found.
     */
    Token find(String token);

    /**
     * Find the token corresponding to the provided user.
     *
     * @param user of the token to be found.
     * @return the token found.
     */
    Token find(User user);

    /**
     * Creating token to reset password for given {@param user}.
     *
     * @param user to reset password.
     * @return the persisted token.
     */
    Token createPasswordResetToken(User user);

    /**
     * Validate expiration of given token.
     *
     * @param token to be validated.
     */
    void validateToken(Token token);

    /**
     * Delete diven token
     *
     * @param token to be deleted.
     */
    void delete(Token token);
}
