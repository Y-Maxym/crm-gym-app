package com.gym.crm.app.service;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.service.common.UserProfileService;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKeyFactory;
import java.lang.reflect.UndeclaredThrowableException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    private static final char[] LOWERCASE_LETTERS = {'a', 'z'};
    private static final char[] UPPER_LETTERS = {'A', 'Z'};
    private static final char[] DIGITS = {'0', '9'};

    @Mock
    private EntityDao<Long, User> repository;

    @InjectMocks
    private UserProfileService service;

    @Test
    @DisplayName("Test generate password with valid length")
    public void givenLength_whenGeneratePassword_thenReturnsPasswordWithCorrectSaltAndHashLength() {
        // given
        int length = 12;

        // when
        String password = service.generatePassword(length);

        String[] parts = password.split(":");
        String salt = parts[0];
        String hash = parts[1];

        // then
        assertThat(parts).hasSize(2);
        assertThat(salt).hasSize(24);
        assertThat(hash).isNotEmpty();
    }

    @RepeatedTest(5)
    @DisplayName("Test generate password contains allowed characters")
    public void givenLength_whenGeneratePassword_thenContainsOnlyAllowedCharactersBeforeHashing() {
        // given
        int length = 100;

        // when
        String password = new RandomStringGenerator.Builder()
                .withinRange(LOWERCASE_LETTERS, UPPER_LETTERS, DIGITS)
                .get()
                .generate(length);

        // then
        assertThat(password).matches("[a-zA-Z0-9]+");
    }

    @Test
    @DisplayName("Test password verification with correct password")
    @SuppressWarnings("all")
    public void givenStoredPassword_whenIsPasswordCorrect_thenReturnsTrueForCorrectPassword() {
        // given
        String plainPassword = "mySecurePassword";

        String salt = ReflectionTestUtils.invokeMethod(service, "generateSalt");
        String hashedPassword = ReflectionTestUtils.invokeMethod(service, "hashPassword", plainPassword, salt);

        String storedPassword = "%s:%s".formatted(salt, hashedPassword);

        // when
        boolean result = service.isPasswordCorrect(plainPassword, storedPassword);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test hashPassword method handles exceptions correctly")
    @SuppressWarnings("all")
    public void givenValidInputs_whenHashPassword_thenDoesNotThrowException() throws NoSuchAlgorithmException {
        // given
        String plainPassword = "password";
        String salt = "salt";

        MockedStatic<SecretKeyFactory> mockStatic = mockStatic(SecretKeyFactory.class);

        given(SecretKeyFactory.getInstance(anyString()))
                .willThrow(new NoSuchAlgorithmException());

        // when
        UndeclaredThrowableException ex = assertThrows(UndeclaredThrowableException.class, () -> {
            ReflectionTestUtils.invokeMethod(service, "hashPassword", plainPassword, salt);
        });

        // then
        Throwable cause = ex.getCause();
        assertThat(cause).isInstanceOf(NoSuchAlgorithmException.class);

        mockStatic.close();
    }

    @Test
    @DisplayName("Test generate username with serial number functionality")
    @SuppressWarnings("all")
    public void givenFirstNameAndLastName_whenAddSerialNumberToUsername_thenReturnsUsername() {
        // given
        String username = "John.Doe";

        AtomicLong serialNumber = (AtomicLong) ReflectionTestUtils.getField(service, "serialNumber");
        serialNumber.set(1L);

        // when
        String actualUsername = ReflectionTestUtils.invokeMethod(service, "addSerialNumberToUsername", username);

        // then
        assertThat(actualUsername).isEqualTo("John.Doe1");
    }


    @Test
    @DisplayName("Test generate username without duplication")
    public void givenFirstNameAndLastName_whenGenerateUsername_thenReturnsUsernameWithSerialNumberWithoutNumber() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        given(repository.findAll())
                .willReturn(Collections.emptyList());

        // when
        String username = service.generateUsername(firstName, lastName);

        // then
        assertThat(username).isEqualTo("John.Doe");
    }

    @Test
    @DisplayName("Test generate username with duplication")
    @SuppressWarnings("all")
    public void givenDuplicatedUsername_whenGenerateUsername_thenUserProfileServiceIsCalled() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        User existingUser = User.builder()
                .username("John.Doe")
                .build();

        given(repository.findAll())
                .willReturn(Collections.singletonList(existingUser));

        AtomicLong serialNumber = (AtomicLong) ReflectionTestUtils.getField(service, "serialNumber");
        serialNumber.set(1L);

        // when
        String username = service.generateUsername(firstName, lastName);

        // then
        assertThat(username).isEqualTo("John.Doe1");
    }

    @Test
    @DisplayName("Test is duplicated username when username is not duplicated")
    public void givenUsername_whenIsDuplicatedUsername_thenReturnsFalse() {
        // given
        String username = "uniqueUsername";

        given(repository.findAll())
                .willReturn(Collections.emptyList());

        // when
        Boolean isDuplicated = ReflectionTestUtils.invokeMethod(service, "isDuplicatedUsername", username);

        // then
        assertThat(isDuplicated).isFalse();
    }

    @Test
    @DisplayName("Test isDuplicatedUsername when username is duplicated")
    public void givenUsername_whenIsDuplicatedUsername_thenReturnsTrue() {
        // given
        String username = "John.Doe";

        User existingUser = User.builder()
                .username("John.Doe")
                .build();

        given(repository.findAll())
                .willReturn(Collections.singletonList(existingUser));

        // when
        Boolean isDuplicated = ReflectionTestUtils.invokeMethod(service, "isDuplicatedUsername", username);

        // then
        assertThat(isDuplicated).isTrue();
    }
}
