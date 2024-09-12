package com.gym.crm.app.service;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.EntityDao;
import lombok.Setter;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
public class UserProfileService {

    private static final char[] LOWERCASE_LETTERS = {'a', 'z'};
    private static final char[] UPPER_LETTERS = {'A', 'Z'};
    private static final char[] DIGITS = {'0', '9'};

    private static final String USERNAME_TEMPLATE = "%s.%s";

    private static long serialNumber = 1L;

    private EntityDao<Long, User> repository;

    public String generatePassword(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(LOWERCASE_LETTERS, UPPER_LETTERS, DIGITS)
                .get();

        return generator.generate(length);
    }

    public String generateUsername(String firstName, String lastName) {
        String username = firstName + "." + lastName;

        if (isDuplicatedUsername(username)) {
            username = generateUsernameWithSerialNumber(firstName, lastName);
        }

        return username;
    }

    private boolean isDuplicatedUsername(String username) {
        return repository.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private String generateUsernameWithSerialNumber(String firstName, String lastName) {
        String username = USERNAME_TEMPLATE.formatted(firstName, lastName);

        return username + serialNumber++;
    }
}
