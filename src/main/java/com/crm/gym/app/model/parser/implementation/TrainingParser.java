package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.entity.TrainingType;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TrainingParser implements Parser<String, Training> {

    @Override
    public Training parse(String input) {
        String[] data = input.split(",");

        return Training.builder()
                .traineeId(Long.parseLong(data[0]))
                .trainerId(Long.parseLong(data[1]))
                .trainingName(data[2])
                .trainingType(TrainingType.valueOf(data[3].toUpperCase()))
                .trainingDate(LocalDateTime.parse(data[4]))
                .trainingDuration(Duration.parse(data[5]))
                .build();
    }
}
