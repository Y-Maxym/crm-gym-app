package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TraineeParser implements Parser<String, Trainee> {

    private static final int TRAINEE_ID_INDEX = 4;
    private static final int USER_ID_INDEX = 0;
    private static final int DATE_OF_BIRTH_INDEX = 5;
    private static final int ADDRESS_INDEX = 6;

    @Override
    public Trainee parse(String input) {
        String[] data = input.split(",");

        return Trainee.builder()
                .id(Long.parseLong(data[TRAINEE_ID_INDEX]))
                .userId(Long.parseLong(data[USER_ID_INDEX]))
                .dateOfBirth(LocalDate.parse(data[DATE_OF_BIRTH_INDEX]))
                .address(data[ADDRESS_INDEX])
                .build();
    }
}
