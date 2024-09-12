package com.gym.crm.app.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    @DisplayName("Test generate password with valid length")
    public void givenLength_whenGeneratePassword_thenReturnsPasswordOfCorrectLength() {
        // given
        int length = 12;

        // when
        String password = userProfileService.generatePassword(length);

        // then
        assertThat(password).hasSize(length);
    }

    @RepeatedTest(5)
    @DisplayName("Test generate password contains allowed characters")
    public void givenLength_whenGeneratePassword_thenContainsOnlyAllowedCharacters() {
        // given
        int length = 100;

        // when
        String password = userProfileService.generatePassword(length);

        // then
        assertThat(password).matches("[a-zA-Z0-9]+");
    }

    @Test
    @DisplayName("Test generate username with serial number functionality")
    public void givenFirstNameAndLastName_whenGenerateUsernameWithSerialNumber_thenReturnsUsernameWithSerialNumberWithoutNumber() {
        // given
        String firstName = "John";
        String lastName = "Doe";

        ReflectionTestUtils.setField(userProfileService, "serialNumber", 1);

        // when
        String username = userProfileService.generateUsernameWithSerialNumber(firstName, lastName);

        // then
        assertThat(username).isEqualTo("John.Doe1");
    }
}
