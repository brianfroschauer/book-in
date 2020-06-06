package com.austral.bookin.service.signup;

import com.austral.bookin.entity.User;

public interface SignupService {

    /**
     * Sign up the provided {@param user}.
     * @param user to be registered.
     *
     * @return the registered user.
     */
    User signup(User user);
}
