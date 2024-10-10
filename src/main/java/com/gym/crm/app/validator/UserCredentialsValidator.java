package com.gym.crm.app.validator;

import com.gym.crm.app.rest.model.UserCredentials;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

@Component
public class UserCredentialsValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserCredentials.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserCredentials request = (UserCredentials) target;

        String username = request.getUsername();
        String password = request.getPassword();

        if (isNull(username) || username.isBlank()) {
            errors.rejectValue("username", "username.empty.error", "Username is required");
        }
        if (isNull(password) || password.isBlank()) {
            errors.rejectValue("password", "password.empty.error", "Password is required");
        }
    }
}