package com.gym.crm.app.util;

import com.gym.crm.app.exception.ParseException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_INVALID_FORMAT_DATE;
import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_INVALID_FORMAT_DATETIME;
import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_INVALID_NUMBER;
import static com.gym.crm.app.util.Constants.ERROR_PARSE_UTILS_NULL_VALUE;
import static java.util.Objects.isNull;

@Slf4j
@Component
@Setter(onMethod_ = @Autowired)
public class ParseUtils {

    private MessageUtils messageUtils;

    public void checkNotNull(String value) {
        if (isNull(value)) {
            throw new ParseException(messageUtils.getMessage(ERROR_PARSE_UTILS_NULL_VALUE));
        }
    }

    public int parseInt(String value) {
        checkNotNull(value);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ParseException(messageUtils.getMessage(ERROR_PARSE_UTILS_INVALID_NUMBER, value));
        }
    }

    public long parseLong(String value) {
        checkNotNull(value);

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ParseException(messageUtils.getMessage(ERROR_PARSE_UTILS_INVALID_NUMBER, value));
        }
    }

    public LocalDate parseDate(String value) {
        checkNotNull(value);

        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new ParseException(messageUtils.getMessage(ERROR_PARSE_UTILS_INVALID_FORMAT_DATE, value));
        }
    }

    public LocalDateTime parseDateTime(String value) {
        checkNotNull(value);

        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new ParseException(messageUtils.getMessage(ERROR_PARSE_UTILS_INVALID_FORMAT_DATETIME, value));
        }
    }
}
