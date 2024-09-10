package com.gym.crm.app.model.parser.impl;

import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.parser.Parser;
import com.gym.crm.app.util.UserUtils;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class UserParser implements Parser<String, User> {

    private static final int FIRST_NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;

    private UserUtils userUtils;

    @Override
    public User parse(@NonNull String input) {
        String[] data = input.split(",");

        String firstName = extractStringValue(data, FIRST_NAME_INDEX);
        String lastName = extractStringValue(data, LAST_NAME_INDEX);
        String username = userUtils.generateUsername(firstName, lastName);
        String password = userUtils.generatePassword(10);

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .isActive(true)
                .build();
    }
}
