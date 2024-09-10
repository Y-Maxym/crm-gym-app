package com.gym.crm.app.model.parser;

import lombok.NonNull;

import java.util.function.Function;

public interface Parser<I, O> {

    O parse(@NonNull I input);

    default <T> T extractAndParseValue(String[] data, int index, Function<String, T> parser) {
        return index < data.length && !data[index].isBlank() ? parser.apply(data[index]) : null;
    }

    default String extractStringValue(String[] data, int index) {
        return index < data.length ? data[index] : null;
    }
}
