package com.gym.crm.app.service;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.repository.EntityDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private EntityDao<Long, User> repository;

    @InjectMocks
    private UserProfileService service;

    @Test
    @DisplayName("Test generate password with valid length")
    public void givenLength_whenGeneratePassword_thenReturnsPasswordOfCorrectLength() {
        // given
        int length = 12;

        // when
        String password = service.generatePassword(length);

        // then
        assertThat(password).hasSize(length);
    }

    @RepeatedTest(5)
    @DisplayName("Test generate password contains allowed characters")
    public void givenLength_whenGeneratePassword_thenContainsOnlyAllowedCharacters() {
        // given
        int length = 100;

        // when
        String password = service.generatePassword(length);

        // then
        assertThat(password).matches("[a-zA-Z0-9]+");
    }

    @Test
    @DisplayName("Test generate username with serial number functionality")
    public void givenFirstNameAndLastName_whenGenerateUsernameWithSerialNumber_thenReturnsUsernameWithSerialNumber() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        ReflectionTestUtils.setField(service, "serialNumber", 1);

        // when
        String username = ReflectionTestUtils.invokeMethod(service, "generateUsernameWithSerialNumber", firstName, lastName);

        // then
        assertThat(username).isEqualTo("John.Doe1");
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

        ReflectionTestUtils.setField(service, "serialNumber", 1);

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
