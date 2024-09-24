package com.gym.crm.app.service.common;

import com.gym.crm.app.dto.AuthCredentials;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class AuthService {

    private static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
    private UserService service;
    private UserProfileService profileService;

    public User authenticate(AuthCredentials credentials) {
        String username = credentials.username();
        String password = credentials.password();

        User user;
        try {
            user = service.findByUsername(username);
        } catch (Exception e) {
            throw new AuthenticationException(INVALID_USERNAME_OR_PASSWORD, e);
        }

        String storedPassword = user.getPassword();

        if (!profileService.isPasswordCorrect(password, storedPassword)) {
            throw new AuthenticationException(INVALID_USERNAME_OR_PASSWORD);
        }

        return user;
    }
}
