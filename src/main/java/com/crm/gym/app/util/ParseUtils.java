package com.crm.gym.app.util;

import com.crm.gym.app.model.exception.ParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class ParseUtils {

    public void checkNotNull(String value) {
        if (isNull(value)) {
            throw logAndReturnParseException("Value cannot be null");
        }
    }

    public int parseInt(String value) {
        checkNotNull(value);

        try {
            return Integer.parseInt(value);

        } catch (NumberFormatException e) {
            throw logAndReturnParseException("{} is not a valid number", value);
        }
    }

    public long parseLong(String value) {
        checkNotNull(value);

        try {
            return Long.parseLong(value);

        } catch (NumberFormatException e) {
            throw logAndReturnParseException("{} is not a valid number", value);
        }
    }

    public boolean parseBoolean(String value) {
        checkNotNull(value);

        try {
            return Boolean.parseBoolean(value);

        } catch (NumberFormatException e) {
            throw logAndReturnParseException("{} is not a valid boolean", value);
        }
    }

    public LocalDate parseDate(String value) {
        checkNotNull(value);

        try {
            return LocalDate.parse(value);

        } catch (DateTimeParseException e) {
            throw logAndReturnParseException("Date is not in a valid format: {}", value);
        }
    }

    public LocalDateTime parseDateTime(String value) {
        checkNotNull(value);

        try {
            return LocalDateTime.parse(value);

        } catch (DateTimeParseException e) {
            throw logAndReturnParseException("DateTime is not in a valid format: {}", value);
        }
    }

    private ParseException logAndReturnParseException(String message, Object... args) {
        log.error(message, args);

        return new ParseException(message);
    }
}
