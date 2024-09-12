package com.gym.crm.app.parser.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.service.UserProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserParserTest {

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserParser parser;

    @Test
    @DisplayName("Test parse method with null input")
    @SuppressWarnings("all")
    public void givenNullInput_whenParse_thenThrowsNullPointerException() {
        // when & then
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    @DisplayName("Test parse with correct data functionality")
    public void givenCorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "John,Doe,2000-01-01,Address1";

        String firstName = "John";
        String lastName = "Doe";
        String username = "John.Doe";
        int passwordLength = 10;
        String password = "1234567890";

        given(userProfileService.generateUsernameWithSerialNumber(firstName, lastName))
                .willReturn(username);

        given(userProfileService.generatePassword(passwordLength))
                .willReturn(password);

        // when
        User actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.isActive()).isTrue();
    }

    @Test
    @DisplayName("Test parse with missing values functionality")
    public void givenMissingValuesInput_whenParse_thenNullValuesIsReturned() {
        // given
        String input = "John,,2000-01-01,Address1";

        String firstName = "John";
        String lastName = "";
        String username = "John.";
        int passwordLength = 10;
        String password = "1234567890";

        given(userProfileService.generateUsernameWithSerialNumber(firstName, lastName))
                .willReturn(username);

        given(userProfileService.generatePassword(passwordLength))
                .willReturn(password);

        // when
        User actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEmpty();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.isActive()).isTrue();
    }

    @Test
    @DisplayName("Test parse with empty input data functionality")
    public void givenEmptyInput_whenParse_thenEmptyUserIsReturned() {
        // given
        String input = "";

        // when
        User actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isBlank();
        assertThat(actual.getLastName()).isBlank();
        assertThat(actual.getUsername()).isBlank();
        assertThat(actual.getPassword()).isBlank();
        assertThat(actual.isActive()).isTrue();
    }
}
