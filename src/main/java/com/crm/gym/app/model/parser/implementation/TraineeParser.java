package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.ParseUtils;
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

    private ParseUtils utils;

    @Override
    public Trainee parse(@NonNull String input) {
        String[] data = input.split(",");

        LocalDate dateOfBirth = extractAndParseValue(data, DATE_OF_BIRTH_INDEX, utils::parseDate);
        String address = extractStringValue(data, ADDRESS_INDEX);

        return Trainee.builder()
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();
    }
}
