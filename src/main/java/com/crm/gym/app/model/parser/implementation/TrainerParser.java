package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

@Component
public class TrainerParser implements Parser<String, Trainer> {

    @Override
    public Trainer parse(String input) {
        String[] data = input.split(",");

        return Trainer.builder()
                .id(Long.parseLong(data[0]))
                .userId(Long.parseLong(data[1]))
                .specializationId(Long.parseLong(data[2]))
                .build();
    }
}
