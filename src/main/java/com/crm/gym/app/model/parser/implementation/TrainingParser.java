package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TrainingParser implements Parser<String, Training> {

    private static final int TRAINING_ID_INDEX = 0;
    private static final int TRAINEE_ID_INDEX = 1;
    private static final int TRAINER_ID_INDEX = 2;
    private static final int TRAINING_NAME_INDEX = 3;
    private static final int TRAINING_TYPE_ID_INDEX = 4;
    private static final int TRAINING_DATE_INDEX = 5;
    private static final int TRAINING_DURATION_INDEX = 6;

    @Override
    public Training parse(String input) {
        String[] data = input.split(",");

        return Training.builder()
                .id(Long.parseLong(data[TRAINING_ID_INDEX]))
                .traineeId(Long.parseLong(data[TRAINEE_ID_INDEX]))
                .trainerId(Long.parseLong(data[TRAINER_ID_INDEX]))
                .trainingName(data[TRAINING_NAME_INDEX])
                .trainingTypeId(Long.parseLong(data[TRAINING_TYPE_ID_INDEX]))
                .trainingDate(LocalDateTime.parse(data[TRAINING_DATE_INDEX]))
                .trainingDuration(Duration.ofHours(Integer.parseInt(data[TRAINING_DURATION_INDEX])))
                .build();
    }
}
