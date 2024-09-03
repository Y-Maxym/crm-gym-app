package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;


@Component
public class TrainerParser implements Parser<String, Trainer> {

    private static final int TRAINER_ID_INDEX = 4;
    private static final int USER_ID_INDEX = 0;
    private static final int SPECIALIZATION_ID_INDEX = 5;

    @Override
    public Trainer parse(String input) {
        String[] data = input.split(",");

        return Trainer.builder()
                .id(Long.parseLong(data[TRAINER_ID_INDEX]))
                .userId(Long.parseLong(data[USER_ID_INDEX]))
                .specializationId(Long.parseLong(data[SPECIALIZATION_ID_INDEX]))
                .build();
    }
}
