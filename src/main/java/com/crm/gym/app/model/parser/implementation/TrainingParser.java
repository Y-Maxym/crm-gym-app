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

        Long id = utils.parseLong(data[TRAINING_ID_INDEX]);
        Long traineeId = utils.parseLong(data[TRAINEE_ID_INDEX]);
        Long trainerId = utils.parseLong(data[TRAINER_ID_INDEX]);
        String trainingName = data[TRAINING_NAME_INDEX];
        Long trainingTypeId = utils.parseLong(data[TRAINING_TYPE_ID_INDEX]);
        LocalDateTime trainingDate = utils.parseDateTime(data[TRAINING_DATE_INDEX]);
        Duration trainingDuration = Duration.ofHours(utils.parseInt(data[TRAINING_DURATION_INDEX]));

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
