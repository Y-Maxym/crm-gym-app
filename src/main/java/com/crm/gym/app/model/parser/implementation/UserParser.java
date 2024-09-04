package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.ParseUtils;
import com.crm.gym.app.util.UserUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserParser implements Parser<String, User> {

    private static final int USER_ID_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;
    private static final int IS_ACTIVE_INDEX = 3;

    private final ParseUtils utils;
    private final UserUtils userUtils;

    @Override
    public User parse(@NonNull String input) {
        String[] data = input.split(",");

        Long id = Long.parseLong(data[USER_ID_INDEX]);
        String firstName = data[FIRST_NAME_INDEX];
        String lastName = data[LAST_NAME_INDEX];
        String username = userUtils.generateUsername(firstName, lastName);
        String password = userUtils.generatePassword(10);
        boolean isActive = utils.parseBoolean(data[IS_ACTIVE_INDEX]);

        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .isActive(isActive)
                .build();
    }
}
