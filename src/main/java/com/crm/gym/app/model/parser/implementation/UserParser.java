package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;

@Component
public class UserParser implements Parser<String, User> {

    @Override
    public User parse(String input) {
        String[] data = input.split(",");

        return User.builder()
                .id(Long.parseLong(data[0]))
                .firstName(data[1])
                .lastName(data[2])
                .username(data[1] + "." + data[2])
                .password(data[3])
                .isActive(Boolean.parseBoolean(data[4]))
                .build();
    }
}
