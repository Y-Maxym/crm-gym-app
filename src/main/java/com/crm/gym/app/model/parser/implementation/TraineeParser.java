package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TraineeParser implements Parser<String, Trainee> {

    @Override
    public Trainee parse(String input) {
        String[] data = input.split(",");

        return Trainee.builder()
                .id(Long.parseLong(data[0]))
                .userId(Long.parseLong(data[1]))
                .dateOfBirth(LocalDate.parse(data[2]))
                .address(data[3])
                .build();
    }
}
