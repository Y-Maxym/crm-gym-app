package com.gym.crm.app.parser.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.parser.Parser;
import com.gym.crm.app.service.common.UserProfileService;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class UserParser implements Parser<String, User> {

    private static final int FIRST_NAME_INDEX = 0;
    private static final int LAST_NAME_INDEX = 1;

    private static final String SPLIT_REGEX = ",";

    private UserProfileService userProfileService;

    @Override
    public User parse(@NonNull String inputLine) {
        String[] entityFields = inputLine.split(SPLIT_REGEX);

        String firstName = extractStringValue(entityFields, FIRST_NAME_INDEX);
        String lastName = extractStringValue(entityFields, LAST_NAME_INDEX);
        String username = userProfileService.generateUsername(firstName, lastName);
        String password = userProfileService.generatePassword(10);

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .isActive(true)
                .build();
    }
}
