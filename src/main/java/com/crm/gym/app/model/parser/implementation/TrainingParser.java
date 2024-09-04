package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.ParseUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TrainingParser implements Parser<String, Training> {

    private static final int TRAINING_ID_INDEX = 0;
    private static final int TRAINEE_ID_INDEX = 1;
    private static final int TRAINER_ID_INDEX = 2;
    private static final int TRAINING_NAME_INDEX = 3;
    private static final int TRAINING_TYPE_ID_INDEX = 4;
    private static final int TRAINING_DATE_INDEX = 5;
    private static final int TRAINING_DURATION_INDEX = 6;

    private final ParseUtils utils;

    @Override
    public Training parse(@NonNull String input) {
        String[] data = input.split(",");

        Long id = getValue(data, TRAINING_ID_INDEX, utils::parseLong);
        Long traineeId = getValue(data, TRAINEE_ID_INDEX, utils::parseLong);
        Long trainerId = getValue(data, TRAINER_ID_INDEX, utils::parseLong);
        String trainingName = getStringValue(data, TRAINING_NAME_INDEX);
        Long trainingTypeId = getValue(data, TRAINING_TYPE_ID_INDEX, utils::parseLong);
        LocalDateTime trainingDate = getValue(data, TRAINING_DATE_INDEX, utils::parseDateTime);
        Duration trainingDuration = Duration.ofHours(getValue(data, TRAINING_DURATION_INDEX, utils::parseInt));

        return Training.builder()
                .id(id)
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingName(trainingName)
                .trainingTypeId(trainingTypeId)
                .trainingDate(trainingDate)
                .trainingDuration(trainingDuration)
                .build();
    }
}
