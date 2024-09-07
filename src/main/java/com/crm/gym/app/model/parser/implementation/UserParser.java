package com.crm.gym.app.model.parser.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.parser.Parser;
import com.crm.gym.app.util.ParseUtils;
import com.crm.gym.app.util.UserUtils;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter(onMethod_ = @Autowired)
public class UserParser implements Parser<String, User> {

    private static final int USER_ID_INDEX = 0;
    private static final int FIRST_NAME_INDEX = 1;
    private static final int LAST_NAME_INDEX = 2;

    private ParseUtils parseUtils;
    private UserUtils userUtils;

    @Override
    public User parse(@NonNull String input) {
        String[] data = input.split(",");

        Long id = extractAndParseValue(data, USER_ID_INDEX, parseUtils::parseLong);
        String firstName = extractStringValue(data, FIRST_NAME_INDEX);
        String lastName = extractStringValue(data, LAST_NAME_INDEX);
        String username = userUtils.generateUsername(firstName, lastName);
        String password = userUtils.generatePassword(10);

        return User.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .isActive(true)
                .build();
    }
}
