package com.gym.crm.app.util;

import com.gym.crm.app.exception.ParseException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.model.parser.ParserHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_INVALID_FORMAT_DATE;
import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_INVALID_FORMAT_DATETIME;
import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_INVALID_NUMBER;
import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_NULL_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ParserHelperTest {

    @Mock
    private MessageHelper messageHelper;

    @InjectMocks
    private ParserHelper parserHelper;

    @Test
    @DisplayName("Test check not null method with non-null value functionality")
    public void givenNonNull_whenCheckNotNull_thenThrowException() {
        // given
        String value = "value";

        // when & then
        assertDoesNotThrow(() -> parserHelper.checkNotNull(value));
    }

    @Test
    @DisplayName("Test check not null method with null value")
    public void givenNull_whenCheckNotNull_thenThrowException() {
        // given
        String message = "Value cannot be null";

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_NULL_VALUE))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.checkNotNull(null));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse int with valid integer value")
    public void givenValidInteger_whenParseInt_thenReturnParsedInteger() {
        // given
        String value = "123";
        int expected = 123;

        // when
        int actual = parserHelper.parseInt(value);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test parse int with null value")
    public void givenNull_whenParseInt_thenThrowParseException() {
        // given
        String message = "Value cannot be null";

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_NULL_VALUE))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseInt(null));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse int with invalid number format")
    public void givenInvalidNumberFormat_whenParseInt_thenThrowParseException() {
        // given
        String value = "invalidNumber";
        String message = "Number is not a valid: %s".formatted(value);

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_INVALID_NUMBER, value))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseInt(value));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse long with valid value")
    public void givenValidLongString_whenParseLong_thenReturnsLongValue() {
        // given
        String value = "12345";
        long expected = 12345L;

        // when
        long result = parserHelper.parseLong(value);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test parse long with null value")
    public void givenNull_whenParseLong_thenThrowParseException() {
        // given
        String message = "Value cannot be null";

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_NULL_VALUE))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseLong(null));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse long with invalid number format")
    public void givenInvalidNumberFormat_whenParseLong_thenThrowParseException() {
        // given
        String value = "invalidNumber";
        String message = "Number is not a valid: %s".formatted(value);

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_INVALID_NUMBER, value))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseLong(value));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse date with valid value")
    public void givenValidDateString_whenParseDate_thenReturnsDateValue() {
        // given
        String value = "2024-09-07";
        LocalDate expected = LocalDate.of(2024, 9, 7);

        // when
        LocalDate result = parserHelper.parseDate(value);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test parse date with null value")
    public void givenNull_whenParseDate_thenThrowParseException() {
        // given
        String message = "Value cannot be null";

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_NULL_VALUE))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseDate(null));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse date with invalid format")
    public void givenInvalidDateFormat_whenParseDate_thenThrowParseException() {
        // given
        String value = "invalidFormat";
        String message = "Date is not in a valid format: %s".formatted(value);

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_INVALID_FORMAT_DATE, value))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseDate(value));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse date time with valid value")
    public void givenValidDateTimeString_whenParseDateTime_thenReturnsDateTimeValue() {
        // given
        String value = "2024-09-07T10:00:00";
        LocalDateTime expected = LocalDateTime.of(2024, 9, 7, 10, 0, 0);

        // when
        LocalDateTime result = parserHelper.parseDateTime(value);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test parse date time with null value")
    public void givenNull_whenParseDateTime_thenThrowParseException() {
        // given
        String message = "Value cannot be null";

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_NULL_VALUE))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseDateTime(null));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Test parse date time with invalid format")
    public void givenInvalidDateTimeFormat_whenParseDateTime_thenThrowParseException() {
        // given
        String value = "invalidFormat";
        String message = "DateTime is not in a valid format: %s".formatted(value);

        given(messageHelper.getMessage(ERROR_PARSE_UTILS_INVALID_FORMAT_DATETIME, value))
                .willReturn(message);

        // when
        ParseException ex = assertThrows(ParseException.class, () -> parserHelper.parseDateTime(value));

        // then
        assertThat(ex.getMessage()).isEqualTo(message);
    }

}