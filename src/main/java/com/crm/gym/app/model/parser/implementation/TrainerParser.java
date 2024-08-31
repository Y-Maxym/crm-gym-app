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
                .username(data[0] + "." + data[1])
                .password(data[2])
                .isActive(Boolean.parseBoolean(data[3]))
                .specialization(TrainingType.valueOf(data[4].toUpperCase()))
                .userId(Long.parseLong(data[5]))
                .build();
    }
}
