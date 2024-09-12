package com.gym.crm.app.model.parser.impl;

import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.parser.Parser;
import com.gym.crm.app.model.parser.ParserHelper;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class TrainerParser implements Parser<String, Trainer> {

    private static final int SPECIALIZATION_ID_INDEX = 2;

    private static final String SPLIT_REGEX = ",";

    private ParserHelper utils;

    @Override
    public Trainer parse(@NonNull String input) {
        String[] data = input.split(SPLIT_REGEX);

        Long specializationId = parseValue(data, SPECIALIZATION_ID_INDEX, utils::parseLong);

        return Trainer.builder()
                .specializationId(specializationId)
                .build();
    }
}
