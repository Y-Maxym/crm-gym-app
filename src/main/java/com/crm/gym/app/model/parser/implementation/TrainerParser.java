package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.LoggingMessageUtils;
import com.crm.gym.app.util.ParseUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.crm.gym.app.util.Constants.PARSED_TRAINER;
import static com.crm.gym.app.util.Constants.PARSING_INPUT;

@Component
@RequiredArgsConstructor
@Slf4j
public class TrainerParser implements Parser<String, Trainer> {

    private static final int TRAINER_ID_INDEX = 4;
    private static final int USER_ID_INDEX = 0;
    private static final int SPECIALIZATION_ID_INDEX = 5;

    private final ParseUtils utils;
    private final LoggingMessageUtils messageUtils;

    @Override
    public Trainer parse(@NonNull String input) {
        log.debug(messageUtils.getMessage(PARSING_INPUT, input));

        String[] data = input.split(",");

        Long id = utils.parseLong(data[TRAINER_ID_INDEX]);
        Long userId = utils.parseLong(data[USER_ID_INDEX]);
        Long specializationId = utils.parseLong(data[SPECIALIZATION_ID_INDEX]);

        Trainer trainer = Trainer.builder()
                .id(id)
                .userId(userId)
                .specializationId(specializationId)
                .build();

        log.debug(messageUtils.getMessage(PARSED_TRAINER, trainer));

        return trainer;
    }
}
