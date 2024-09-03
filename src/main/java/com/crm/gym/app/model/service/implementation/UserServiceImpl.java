package com.crm.gym.app.model.service.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.crm.gym.app.util.PasswordGenerator.generatePassword;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EntityDao<Long, User> repository;

    @Override
    public User findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(User user) {
        if (isNull(user.getUsername())) {
            user.setUsername(generateUsername(user));
        }

        if (isDuplicatedUsername(user.getUsername())) {
            user.setUsername(user.getUsername() + User.getAndIncrementSerialNumber());
        }

        if (isNull(user.getPassword())) {
            user.setPassword(generatePassword(10));
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

    private String generateUsername(User user) {
        return user.getFirstName() + "." + user.getLastName();
    }

    private boolean isDuplicatedUsername(String username) {
        return repository.findAll().stream().anyMatch(user -> user.getUsername().equals(username));
    }
}
