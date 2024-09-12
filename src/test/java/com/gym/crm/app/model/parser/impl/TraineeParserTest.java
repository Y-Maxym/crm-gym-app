package com.gym.crm.app.model.parser.impl;

import com.gym.crm.app.exception.ParseException;
import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.parser.ParserHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TraineeParserTest {

    @Mock
    private ParserHelper utils;

    @InjectMocks
    private TraineeParser parser;

    @Test
    @DisplayName("Test parse method with null input")
    @SuppressWarnings("all")
    public void givenNullInput_whenParse_thenThrowsNullPointerException() {
        // given

        // when & then
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    @DisplayName("Test parse with correct data functionality")
    public void givenCorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "John,Doe,2000-01-01,Address1";

        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String address = "Address1";

        given(utils.parseDate(dateOfBirth.toString()))
                .willReturn(dateOfBirth);

        // when
        Trainee actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isNull();
        assertThat(actual.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("Test parse with missing values functionality")
    public void givenMissingValuesInput_whenParse_thenNullValuesIsReturned() {
        // given
        String input = "John,Doe,,Address1";

        String address = "Address1";

        // when
        Trainee actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isNull();
        assertThat(actual.getDateOfBirth()).isNull();
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("Test parse with incorrect data functionality")
    public void givenIncorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "John,Doe,abc,Address1";

        String dateOfBirth = "abc";

        given(utils.parseDate(dateOfBirth))
                .willThrow(new ParseException("Date is not in a valid format: %s".formatted(dateOfBirth)));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse(input));

        // then
        assertThat(ex.getMessage()).isEqualTo("Date is not in a valid format: %s".formatted(dateOfBirth));
    }

    @Test
    @DisplayName("Test parse with empty input data functionality")
    public void givenEmptyInput_whenParse_thenEmptyTraineeIsReturned() {
        // given
        String input = "";

        // when
        Trainee actual = parser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isNull();
        assertThat(actual.getDateOfBirth()).isNull();
        assertThat(actual.getAddress()).isNull();
    }

}