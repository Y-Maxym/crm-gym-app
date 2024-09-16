package com.gym.crm.app.service.common;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.EntityDao;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Setter(onMethod_ = @Autowired)
public class UserProfileService {

    private static final AtomicLong serialNumber = new AtomicLong(1L);

    private static final String USERNAME_TEMPLATE = "%s.%s";

    private PasswordGenerator passwordGenerator;
    private EntityDao<Long, User> repository;

    public String generatePassword() {
        return passwordGenerator.generatePassword();
    }

    public String hashPassword(String password) {
        return passwordGenerator.hashPassword(password);
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
        return username + serialNumber.getAndIncrement();
    }
}
