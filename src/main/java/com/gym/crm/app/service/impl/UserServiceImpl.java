package com.gym.crm.app.service.impl;

import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.logging.MessageHelper;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.service.EntityExceptionHelper;
import com.gym.crm.app.service.UserProfileService;
import com.gym.crm.app.service.UserService;
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
    private UserProfileService userProfileService;
    private EntityExceptionHelper exceptionHelper;

    @Override
    public User findById(Long id) {
        exceptionHelper.checkId(id);

        return repository.findById(id)
                .orElseThrow(() -> new EntityValidationException(messageHelper.getMessage(ERROR_USER_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public void save(User user) {
        exceptionHelper.checkEntity(user);

        User preparedUser = prepareUserForSave(user);

        repository.save(preparedUser);
    }

    private User prepareUserForSave(User user) {
        if (isNull(user.getUsername())) {

            String username = userProfileService.generateUsername(user.getFirstName(), user.getLastName());
            user = user.toBuilder().username(username).build();
        }

        if (isNull(user.getPassword())) {

            String password = userProfileService.generatePassword(10);
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
}
