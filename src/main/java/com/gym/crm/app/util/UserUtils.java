package com.gym.crm.app.util;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    private static final char[] LOWERCASE_LETTERS = {'a', 'z'};
    private static final char[] UPPER_LETTERS = {'A', 'Z'};
    private static final char[] DIGITS = {'0', '9'};

    private static long serialNumber = 1L;

    public String generatePassword(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(LOWERCASE_LETTERS, UPPER_LETTERS, DIGITS)
                .get();

        return generator.generate(length);
    }

    public String generateUsernameWithSerialNumber(String firstName, String lastName) {
        String username = firstName + "." + lastName;

        username += serialNumber++;

        return username;
    }
}
