package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.Parser;
import org.springframework.stereotype.Component;


@Component
public class UserParser implements Parser<String, User> {

    private static final int USER_ID_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;
    private static final int PASSWORD_INDEX = 3;
    private static final int IS_ACTIVE_INDEX = 4;

    @Override
    public User parse(String input) {
        String[] data = input.split(",");

        return User.builder()
                .id(Long.parseLong(data[USER_ID_INDEX]))
                .firstName(data[FIRST_NAME_INDEX])
                .lastName(data[LAST_NAME_INDEX])
                .username(data[FIRST_NAME_INDEX] + "." + data[LAST_NAME_INDEX])
                .password(data[PASSWORD_INDEX])
                .isActive(Boolean.parseBoolean(data[IS_ACTIVE_INDEX]))
                .build();
    }
}
