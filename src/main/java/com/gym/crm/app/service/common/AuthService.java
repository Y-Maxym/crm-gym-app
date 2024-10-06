package com.gym.crm.app.service.common;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class AuthService {

    private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";

    private UserService userService;
    private UserProfileService profileService;

    public User authenticate(UserCredentials credentials) {
        User foundUser = retrieveUserByUsername(credentials.getUsername());

        String storedPassword = foundUser.getPassword();

        if (!profileService.isPasswordCorrect(credentials.getPassword(), storedPassword)) {
            throw new AuthenticationException(INVALID_USERNAME_OR_PASSWORD);
        }

        return foundUser;
    }

    private User retrieveUserByUsername(String username) {
        try {
            return userService.findByUsername(username);
        } catch (EntityValidationException e) {
            throw new AuthenticationException(INVALID_USERNAME_OR_PASSWORD, e);
        }
    }
}
