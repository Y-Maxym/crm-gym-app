package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.LoggingMessageUtils;
import com.crm.gym.app.util.ParseUtils;
import com.crm.gym.app.util.UserUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.crm.gym.app.util.Constants.PARSED_USER;
import static com.crm.gym.app.util.Constants.PARSING_INPUT;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserParser implements Parser<String, User> {

    private static final int USER_ID_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;
    private static final int IS_ACTIVE_INDEX = 3;

    private final ParseUtils utils;
    private final UserUtils userUtils;
    private final LoggingMessageUtils messageUtils;


    @Override
    public User parse(@NonNull String input) {
        log.debug(messageUtils.getMessage(PARSING_INPUT, input));

        String[] data = input.split(",");

        Long id = utils.parseLong(data[USER_ID_INDEX]);
        String firstName = data[FIRST_NAME_INDEX];
        String lastName = data[LAST_NAME_INDEX];
        String username = userUtils.generateUsername(firstName, lastName);
        String password = userUtils.generatePassword(10);
        boolean isActive = utils.parseBoolean(data[IS_ACTIVE_INDEX]);

        User user = User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .isActive(isActive)
                .build();

        log.debug(messageUtils.getMessage(PARSED_USER, user));

        return user;
    }
}
