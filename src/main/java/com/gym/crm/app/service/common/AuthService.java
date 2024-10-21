package com.gym.crm.app.service.common;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.rest.exception.ErrorCode.INVALID_USERNAME_OR_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String INVALID_USERNAME_OR_PASSWORD1 = "Invalid username or password";

    private final UserService userService;
    private final UserProfileService profileService;

    public User authenticate(String username, String password) {
        User foundUser = retrieveUserByUsername(username);

        String storedPassword = foundUser.getPassword();

        if (!profileService.isPasswordCorrect(password, storedPassword)) {
            throw new AuthenticationException(INVALID_USERNAME_OR_PASSWORD1, INVALID_USERNAME_OR_PASSWORD.getCode());
        }

        return foundUser;
    }

    private User retrieveUserByUsername(String username) {
        try {
            return userService.findByUsername(username);
        } catch (EntityValidationException e) {
            throw new AuthenticationException(INVALID_USERNAME_OR_PASSWORD1, INVALID_USERNAME_OR_PASSWORD.getCode(), e);
        }
    }
}
