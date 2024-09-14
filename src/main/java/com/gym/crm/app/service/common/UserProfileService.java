package com.gym.crm.app.service.common;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.EntityDao;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Setter(onMethod_ = @Autowired)
public class UserProfileService {

    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 100_000;
    private static final int KEY_LENGTH = 256;

    private static final char[] LOWERCASE_LETTERS = {'a', 'z'};
    private static final char[] UPPER_LETTERS = {'A', 'Z'};
    private static final char[] DIGITS = {'0', '9'};

    private static final String PASSWORD_PATTERN = "%s:%s";
    private static final String USERNAME_TEMPLATE = "%s.%s";

    private static final AtomicLong serialNumber = new AtomicLong(1L);

    private EntityDao<Long, User> repository;

    public String generatePassword(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange(LOWERCASE_LETTERS, UPPER_LETTERS, DIGITS)
                .get();

        String password = generator.generate(length);

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        return PASSWORD_PATTERN.formatted(salt, hashedPassword);
    }

    public boolean isPasswordCorrect(String inputPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        String salt = parts[0];
        String storedHash = parts[1];

        String inputHash = hashPassword(inputPassword, salt);

        return inputHash.equals(storedHash);
    }

    public String generateUsername(String firstName, String lastName) {
        String username = USERNAME_TEMPLATE.formatted(firstName, lastName);

        if (isDuplicatedUsername(username)) {
            username = addSerialNumberToUsername(username);
        }

        return username;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();

        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        return Base64.getEncoder().encodeToString(saltBytes);
    }

    @SneakyThrows
    private String hashPassword(String password, String salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);

        byte[] hashedPasswordBytes = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hashedPasswordBytes);
    }

    private boolean isDuplicatedUsername(String username) {
        return repository.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    private String addSerialNumberToUsername(String username) {
        return username + serialNumber.getAndIncrement();
    }
}
