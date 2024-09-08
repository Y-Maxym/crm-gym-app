package com.gym.crm.app.model.storage.implementation;

import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.storage.Storage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
public class UserStorage implements Storage<Long, User> {

    private final Map<Long, User> storage = new HashMap<>();

    private static long currentId = 1;

    @Override
    public User get(Long id) {
        return storage.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User put(User entity) {
        if (isNull(entity.getId())) {
            entity.setId(currentId++);
        }

        storage.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public void remove(Long id) {
        storage.remove(id);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
