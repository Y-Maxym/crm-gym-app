package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.exception.ParseException;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.util.ParseUtils;
import com.crm.gym.app.util.UserUtils;
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
    private ParseUtils parseUtils;

    @Mock
    private UserUtils userUtils;

    @InjectMocks
    private UserParser userParser;

    @Test
    @DisplayName("Test parse with correct data functionality")
    public void givenCorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "1,John,Doe,1,2000-01-01,Address1";

        Long userId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String username = "John.Doe";
        int passwordLength = 10;
        String password = "1234567890";


        given(parseUtils.parseLong(userId.toString()))
                .willReturn(userId);

        given(userUtils.generateUsername(firstName, lastName))
                .willReturn(username);

        given(userUtils.generatePassword(passwordLength))
                .willReturn(password);

        // when
        User actual = userParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(userId);
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
        String input = "1,John,,1,2000-01-01,Address1";

        Long userId = 1L;
        String firstName = "John";
        String lastName = "";
        String username = "John.";
        int passwordLength = 10;
        String password = "1234567890";

        given(parseUtils.parseLong(userId.toString()))
                .willReturn(userId);

        given(userUtils.generateUsername(firstName, lastName))
                .willReturn(username);

        given(userUtils.generatePassword(passwordLength))
                .willReturn(password);

        // when
        User actual = userParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(userId);
        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEmpty();
        assertThat(actual.getUsername()).isEqualTo(username);
        assertThat(actual.getPassword()).isEqualTo(password);
        assertThat(actual.isActive()).isTrue();
    }

    @Test
    @DisplayName("Test parse with incorrect data functionality")
    public void givenIncorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "ff,John,Doe,1,2000-01-01,Address1";

        String userId = "ff";

        given(parseUtils.parseLong(userId))
                .willThrow(new ParseException("Number is not a valid: %s".formatted(userId)));
        // when
        ParseException ex = assertThrows(ParseException.class, () -> userParser.parse(input));

        // then
        assertThat(ex.getMessage()).isEqualTo("Number is not a valid: %s".formatted(userId));
    }

    @Test
    @DisplayName("Test parse with empty input data functionality")
    public void givenEmptyInput_whenParse_thenEmptyUserIsReturned() {
        // given
        String input = "";

        // when
        User actual = userParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getFirstName()).isNull();
        assertThat(actual.getLastName()).isNull();
        assertThat(actual.getUsername()).isNull();
        assertThat(actual.getPassword()).isNull();
        assertThat(actual.isActive()).isTrue();
    }

}