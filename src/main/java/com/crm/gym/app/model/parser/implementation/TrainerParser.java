package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.ParseUtils;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class TrainerParser implements Parser<String, Trainer> {

    private static final int TRAINER_ID_INDEX = 3;
    private static final int USER_ID_INDEX = 0;
    private static final int SPECIALIZATION_ID_INDEX = 4;

    private ParseUtils utils;

    @Override
    public Trainer parse(@NonNull String input) {
        String[] data = input.split(",");

        Long id = extractAndParseValue(data, TRAINER_ID_INDEX, utils::parseLong);
        Long userId = extractAndParseValue(data, USER_ID_INDEX, utils::parseLong);
        Long specializationId = extractAndParseValue(data, SPECIALIZATION_ID_INDEX, utils::parseLong);

        return Trainer.builder()
                .id(id)
                .userId(userId)
                .specializationId(specializationId)
                .build();
    }
}
