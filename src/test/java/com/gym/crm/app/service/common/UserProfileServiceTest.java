package com.gym.crm.app.service.common;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserRepositoryImpl repository;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private UserProfileService service;

    @Test
    @DisplayName("Test generate password functionality")
    public void whenGeneratePassword_thenPasswordGeneratorIsCalled() {
        // when
        service.generatePassword();

        // then
        verify(passwordGenerator).generatePassword();
    }

    @Test
    @DisplayName("Test hash password functionality")
    public void givenPassword_whenHashPassword_thenPasswordGeneratorIsCalled() {
        // given
        String password = "password";

        // when
        service.hashPassword(password);

        // then
        verify(passwordGenerator).hashPassword(password);
    }

    @Test
    @DisplayName("Test generate username with serial number functionality")
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
