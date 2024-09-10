package com.gym.crm.app.model.service.impl;

import com.gym.crm.app.exception.EntityNotFoundException;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.service.UserService;
import com.gym.crm.app.util.MessageUtils;
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
    private MessageUtils messageUtils;
    private UserUtils userUtils;

    @Override
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageUtils.getMessage(ERROR_USER_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public void save(User user) {
        User preparedUser = prepareUserForSave(user);

        repository.saveOrUpdate(preparedUser);
    }

    private User prepareUserForSave(User user) {
        if (isNull(user.getUsername())) {

            String username = userUtils.generateUsername(user.getFirstName(), user.getLastName());
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
        repository.saveOrUpdate(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
