package com.gym.crm.app.util;

import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.repository.implementation.UserDaoImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserUtilsTest {

    @Mock
    private UserDaoImpl repository;

    @InjectMocks
    private UserUtils userUtils;

    @Test
    @DisplayName("Test generate password with valid length")
    public void givenLength_whenGeneratePassword_thenReturnsPasswordOfCorrectLength() {
        // given
        int length = 12;

        // when
        String password = userUtils.generatePassword(length);

        // then
        assertThat(password).hasSize(length);
    }

    @RepeatedTest(5)
    @DisplayName("Test generate password contains allowed characters")
    public void givenLength_whenGeneratePassword_thenContainsOnlyAllowedCharacters() {
        // given
        int length = 100;

        // when
        String password = userUtils.generatePassword(length);

        // then
        assertThat(password).matches("[a-zA-Z0-9]+");
    }

    @Test
    @DisplayName("Test generate username without duplication")
    public void givenFirstNameAndLastName_whenGenerateUsername_thenReturnsUsernameWithoutNumber() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        given(repository.findAll())
                .willReturn(Collections.emptyList());

        // when
        String username = userUtils.generateUsername(firstName, lastName);

        // then
        assertThat(username).isEqualTo("John.Doe");
    }

    @Test
    @DisplayName("Test generate username with duplication")
    public void givenDuplicatedUsername_whenGenerateUsername_thenReturnsUsernameWithNumber() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        User existingUser = User.builder()
                .username("John.Doe")
                .build();

        given(repository.findAll())
                .willReturn(Collections.singletonList(existingUser));

        // when
        String username = userUtils.generateUsername(firstName, lastName);

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
        boolean isDuplicated = userUtils.isDuplicatedUsername(username);

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
        boolean isDuplicated = userUtils.isDuplicatedUsername(username);

        // then
        assertThat(isDuplicated).isTrue();
    }
}