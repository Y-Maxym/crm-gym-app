package com.gym.crm.app.parser.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.parser.Parser;
import com.gym.crm.app.parser.ParserHelper;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Setter(onMethod_ = @Autowired)
public class TrainingParser implements Parser<String, Training> {

    private static final int TRAINEE_ID_INDEX = 0;
    private static final int TRAINER_ID_INDEX = 1;
    private static final int TRAINING_NAME_INDEX = 2;
    private static final int TRAINING_TYPE_ID_INDEX = 3;
    private static final int TRAINING_DATE_INDEX = 4;
    private static final int TRAINING_DURATION_INDEX = 5;

    private static final String SPLIT_REGEX = ",";

    private ParserHelper parserHelper;

    @Override
    public Training parse(@NonNull String inputLine) {
        String[] entityFields = inputLine.split(SPLIT_REGEX);

        Long traineeId = parseValue(entityFields, TRAINEE_ID_INDEX, parserHelper::parseLong);
        Long trainerId = parseValue(entityFields, TRAINER_ID_INDEX, parserHelper::parseLong);
        String trainingName = extractStringValue(entityFields, TRAINING_NAME_INDEX);
        Long trainingTypeId = parseValue(entityFields, TRAINING_TYPE_ID_INDEX, parserHelper::parseLong);
        LocalDateTime trainingDate = parseValue(entityFields, TRAINING_DATE_INDEX, parserHelper::parseDateTime);

        Duration trainingDuration = Optional.ofNullable(parseValue(entityFields, TRAINING_DURATION_INDEX, parserHelper::parseInt))
                .map(Duration::ofHours)
                .orElse(null);

        return Training.builder()
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingName(trainingName)
                .trainingTypeId(trainingTypeId)
                .trainingDate(trainingDate)
                .trainingDuration(trainingDuration)
                .build();
    }
}
