package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.exception.EntityNotFoundException;
import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.UserService;
import com.crm.gym.app.util.MessageUtils;
import com.crm.gym.app.util.UserUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.crm.gym.app.util.Constants.ERROR_USER_WITH_ID_NOT_FOUND;
import static java.util.Objects.isNull;

@Slf4j
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
        if (isNull(user.getUsername())) {

            String username = userUtils.generateUsername(user.getFirstName(), user.getLastName());
            user.setUsername(username);
        }

        if (isNull(user.getPassword())) {

            String password = userUtils.generatePassword(10);
            user.setPassword(password);
        }

        repository.save(user);
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
