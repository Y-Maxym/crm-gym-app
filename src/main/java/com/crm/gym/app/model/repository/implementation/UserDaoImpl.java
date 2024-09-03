package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements EntityDao<Long, User> {

    private final Storage<Long, User> storage;

    @Override
    public User findById(Long id) {
        return storage.get(id);
    }

    @Override
    public void save(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        storage.put(user.getId(), user);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
