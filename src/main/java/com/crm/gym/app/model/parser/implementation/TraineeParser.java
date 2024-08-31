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
                .firstName(data[0])
                .lastName(data[1])
                .username(data[0] + "." + data[1])
                .password(data[2])
                .isActive(Boolean.parseBoolean(data[3]))
                .dateOfBirth(LocalDate.parse(data[4]))
                .address(data[5])
                .userId(Long.parseLong(data[6]))
                .build();
    }
}
