package com.gym.crm.app.service;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private static final char[] LOWERCASE_LETTERS = {'a', 'z'};
    private static final char[] UPPER_LETTERS = {'A', 'Z'};
    private static final char[] DIGITS = {'0', '9'};

    private static final String USERNAME_TEMPLATE = "%s.%s";

    private static long serialNumber = 1L;

    public String generatePassword(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(LOWERCASE_LETTERS, UPPER_LETTERS, DIGITS)
                .get();

        return generator.generate(length);
    }

    public String generateUsernameWithSerialNumber(String firstName, String lastName) {
        String username = USERNAME_TEMPLATE.formatted(firstName, lastName);

        return username + serialNumber++;
    }
}
