package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.ParseUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

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

        Long id = extractAndParseValue(data, TRAINING_ID_INDEX, utils::parseLong);
        Long traineeId = extractAndParseValue(data, TRAINEE_ID_INDEX, utils::parseLong);
        Long trainerId = extractAndParseValue(data, TRAINER_ID_INDEX, utils::parseLong);
        String trainingName = extractStringValue(data, TRAINING_NAME_INDEX);
        Long trainingTypeId = extractAndParseValue(data, TRAINING_TYPE_ID_INDEX, utils::parseLong);
        LocalDateTime trainingDate = extractAndParseValue(data, TRAINING_DATE_INDEX, utils::parseDateTime);

        Duration trainingDuration = Optional.ofNullable(extractAndParseValue(data, TRAINING_DURATION_INDEX, utils::parseInt))
                .map(Duration::ofHours)
                .orElse(null);

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
