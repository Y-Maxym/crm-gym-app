package com.gym.crm.app.validator;

import com.gym.crm.app.rest.model.ChangePasswordRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static java.util.Objects.isNull;

@Component
public class ChangePasswordValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ChangePasswordRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        ChangePasswordRequest request = (ChangePasswordRequest) target;

        String username = request.getUsername();
        String password = request.getPassword();
        String newPassword = request.getNewPassword();

        if (isNull(username) || username.isBlank()) {
            errors.rejectValue("username", "username.null.error", "Username is required");
        }
        if (isNull(password) || password.isBlank()) {
            errors.rejectValue("password", "password.null.error", "Password is required");
        }
        if (isNull(newPassword) || newPassword.isBlank()) {
            errors.rejectValue("newPassword", "new.password.null.error", "New password is required");
        }
    }
}
