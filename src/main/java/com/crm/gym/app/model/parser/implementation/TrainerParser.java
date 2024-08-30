package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.entity.TrainingType;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

@Component
public class TrainerParser implements Parser<String, Trainer> {

    @Override
    public Trainer parse(String input) {
        String[] data = input.split(",");

        return Trainer.builder()
                .firstName(data[0])
                .lastName(data[1])
                .username(data[2])
                .password(data[3])
                .isActive(Boolean.parseBoolean(data[4]))
                .specialization(TrainingType.valueOf(data[5].toUpperCase()))
                .userId(Long.parseLong(data[6]))
                .build();
    }
}
