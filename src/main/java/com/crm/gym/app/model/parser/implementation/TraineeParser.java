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

    private static final int TRAINEE_ID_INDEX = 3;
    private static final int USER_ID_INDEX = 0;
    private static final int DATE_OF_BIRTH_INDEX = 4;
    private static final int ADDRESS_INDEX = 5;

    private ParseUtils utils;

    @Override
    public Trainee parse(@NonNull String input) {
        String[] data = input.split(",");

        Long id = extractAndParseValue(data, TRAINEE_ID_INDEX, utils::parseLong);
        Long userId = extractAndParseValue(data, USER_ID_INDEX, utils::parseLong);
        LocalDate dateOfBirth = extractAndParseValue(data, DATE_OF_BIRTH_INDEX, utils::parseDate);
        String address = extractStringValue(data, ADDRESS_INDEX);

        return Trainee.builder()
                .id(id)
                .userId(userId)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();
    }
}
