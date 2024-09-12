package com.gym.crm.app.model.service.impl;

import com.gym.crm.app.exception.EntityException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.service.EntityExceptionHelper;
import com.gym.crm.app.model.service.UserService;
import com.gym.crm.app.util.UserUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gym.crm.app.util.Constants.ERROR_USER_WITH_ID_NOT_FOUND;
import static java.util.Objects.isNull;

@Service
@Setter(onMethod_ = @Autowired)
public class UserServiceImpl implements UserService {

    private EntityDao<Long, User> repository;
    private MessageHelper messageHelper;
    private UserUtils userUtils;
    private EntityExceptionHelper exceptionHelper;

    @Override
    public User findById(Long id) {
        exceptionHelper.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityException(messageHelper.getMessage(ERROR_USER_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public void save(User user) {
        exceptionHelper.checkEntity(user);

        User preparedUser = prepareUserForSave(user);

        repository.save(preparedUser);
    }

    private User prepareUserForSave(User user) {
        if (isNull(user.getUsername())) {

            String username = generateUsername(user.getFirstName(), user.getLastName());
            user = user.toBuilder().username(username).build();
        }

        if (isNull(user.getPassword())) {

            String password = userUtils.generatePassword(10);
            user = user.toBuilder().password(password).build();
        }

        return user;
    }

    @Override
    public void update(User user) {
        exceptionHelper.checkEntity(user);
        exceptionHelper.checkId(user.getId());

        repository.update(user);
    }

    @Override
    public void deleteById(Long id) {
        exceptionHelper.checkId(id);

        repository.deleteById(id);
    }

    private String generateUsername(String firstName, String lastName) {
        String username = firstName + "." + lastName;

        if (isDuplicatedUsername(username)) {
            username = userUtils.generateUsernameWithSerialNumber(firstName, lastName);
        }

        return username;
    }

    private boolean isDuplicatedUsername(String username) {
        return repository.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }

}
