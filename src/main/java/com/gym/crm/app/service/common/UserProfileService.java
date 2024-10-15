package com.gym.crm.app.service.common;

import com.gym.crm.app.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class UserProfileService {

    private static final String USERNAME_TEMPLATE = "%s.%s";

    private PasswordGenerator passwordGenerator;
    private UserRepository repository;

    public String generatePassword() {
        return passwordGenerator.generatePassword();
    }

    public String hashPassword(String password) {
        return passwordGenerator.hashPassword(password);
    }

    public boolean isPasswordCorrect(String inputPassword, String storedPassword) {
        return passwordGenerator.isPasswordCorrect(inputPassword, storedPassword);
    }

    public String generateUsername(String firstName, String lastName) {
        String username = USERNAME_TEMPLATE.formatted(firstName, lastName);

        return isDuplicatedUsername(username)
                ? addSerialNumberToUsername(username)
                : username;
    }

    private boolean isDuplicatedUsername(String username) {
        return repository.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private String addSerialNumberToUsername(String username) {
        Long serialNumber = repository.getNextSerialNumber();

        return username + serialNumber;
    }
}
