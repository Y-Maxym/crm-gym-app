package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Training;
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
                .id(Long.parseLong(data[0]))
                .traineeId(Long.parseLong(data[1]))
                .trainerId(Long.parseLong(data[2]))
                .trainingName(data[3])
                .trainingTypeId(Long.parseLong(data[4]))
                .trainingDate(LocalDateTime.parse(data[5]))
                .trainingDuration(Duration.ofHours(Integer.parseInt(data[6])))
                .build();
    }
}
