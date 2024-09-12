package com.gym.crm.app.parser.impl;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.parser.Parser;
import com.gym.crm.app.parser.ParserHelper;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Setter(onMethod_ = @Autowired)
public class TraineeParser implements Parser<String, Trainee> {

    private static final int DATE_OF_BIRTH_INDEX = 2;
    private static final int ADDRESS_INDEX = 3;

    private static final String SPLIT_REGEX = ",";

    private ParserHelper utils;

    @Override
    public Trainee parse(@NonNull String input) {
        String[] data = input.split(SPLIT_REGEX);

        LocalDate dateOfBirth = parseValue(data, DATE_OF_BIRTH_INDEX, utils::parseDate);
        String address = extractStringValue(data, ADDRESS_INDEX);

        return Trainee.builder()
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();
    }
}
