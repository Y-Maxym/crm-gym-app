package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.exception.ParseException;
import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.util.ParseUtils;
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
    private ParseUtils parseUtils;

    @InjectMocks
    private TraineeParser traineeParser;

    @Test
    @DisplayName("Test parse with correct data functionality")
    public void givenCorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "1,John,Doe,1,2000-01-01,Address1";

        Long userId = 1L;
        Long traineeId = 1L;
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String address = "Address1";

        given(parseUtils.parseLong(userId.toString()))
                .willReturn(userId);

        given(parseUtils.parseLong(traineeId.toString()))
                .willReturn(traineeId);

        given(parseUtils.parseDate(dateOfBirth.toString()))
                .willReturn(dateOfBirth);

        // when
        Trainee actual = traineeParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(userId);
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("Test parse with missing values functionality")
    public void givenMissingValuesInput_whenParse_thenNullValuesIsReturned() {
        // given
        String input = "1,John,Doe,,,Address1";

        Long userId = 1L;
        String address = "Address1";

        given(parseUtils.parseLong(userId.toString()))
                .willReturn(userId);

        // when
        Trainee actual = traineeParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isEqualTo(userId);
        assertThat(actual.getDateOfBirth()).isNull();
        assertThat(actual.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("Test parse with incorrect data functionality")
    public void givenIncorrectInput_whenParse_thenSuccessfulReturn() {
        // given
        String input = "1,John,Doe,1,abc,Address1";

        String dateOfBirth = "abc";

        given(parseUtils.parseDate(dateOfBirth))
                .willThrow(new ParseException("Date is not in a valid format: %s".formatted(dateOfBirth)));

        // when
        ParseException ex = assertThrows(ParseException.class, () -> traineeParser.parse(input));

        // then
        assertThat(ex.getMessage()).isEqualTo("Date is not in a valid format: %s".formatted(dateOfBirth));
    }

    @Test
    @DisplayName("Test parse with empty input data functionality")
    public void givenEmptyInput_whenParse_thenEmptyTraineeIsReturned() {
        // given
        String input = "";

        // when
        Trainee actual = traineeParser.parse(input);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getUserId()).isNull();
        assertThat(actual.getDateOfBirth()).isNull();
        assertThat(actual.getAddress()).isNull();
    }

}