package com.gym.crm.app.parser;

import lombok.NonNull;

import java.util.function.Function;

public interface Parser<I, O> {

    O parse(@NonNull I input);

    default <T> T parseValue(String[] data, int index, Function<String, T> parser) {
        return hasContentToParse(data, index) ? parser.apply(data[index]) : null;
    }

    default String extractStringValue(String[] data, int index) {
        return index < data.length ? data[index] : null;
    }

    private boolean hasContentToParse(String[] data, int index) {
        return index < data.length && !data[index].isBlank();
    }
}
