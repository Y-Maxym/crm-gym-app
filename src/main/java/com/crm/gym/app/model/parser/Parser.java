package com.crm.gym.app.model.parser;

import lombok.NonNull;

import java.util.function.Function;

public interface Parser<I, O> {

    O parse(@NonNull I input);

    default <T> T getValue(String[] data, int index, Function<String, T> parser) {
        return index < data.length && !data[index].isBlank() ? parser.apply(data[index]) : null;
    }

    default String getStringValue(String[] data, int index) {
        return index < data.length ? data[index] : null;
    }
}
