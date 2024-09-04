package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.LoggingMessageUtils;
import com.crm.gym.app.util.ParseUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.crm.gym.app.util.Constants.PARSED_TRAINEE;
import static com.crm.gym.app.util.Constants.PARSING_INPUT;

@Component
@RequiredArgsConstructor
@Slf4j
public class TraineeParser implements Parser<String, Trainee> {

    private static final int TRAINEE_ID_INDEX = 4;
    private static final int USER_ID_INDEX = 0;
    private static final int DATE_OF_BIRTH_INDEX = 5;
    private static final int ADDRESS_INDEX = 6;

    private final ParseUtils utils;
    private final LoggingMessageUtils messageUtils;

    @Override
    public Trainee parse(@NonNull String input) {
        log.debug(messageUtils.getMessage(PARSING_INPUT, input));

        String[] data = input.split(",");

        Long id = utils.parseLong(data[TRAINEE_ID_INDEX]);
        Long userId = utils.parseLong(data[USER_ID_INDEX]);
        LocalDate dateOfBirth = utils.parseDate(data[DATE_OF_BIRTH_INDEX]);
        String address = data[ADDRESS_INDEX];

        Trainee trainee = Trainee.builder()
                .id(id)
                .userId(userId)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        log.debug(messageUtils.getMessage(PARSED_TRAINEE, trainee));

        return trainee;
    }
}
