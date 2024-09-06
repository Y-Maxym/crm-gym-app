package com.crm.gym.app.model.storage.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserStorage implements Storage<Long, User> {

    private final Map<Long, User> storage = new HashMap<>();

    @Override
    public User get(Long key) {
        return storage.get(key);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public User put(Long key, User user) {
        return storage.put(key, user);
    }

    @Override
    public void remove(Long key) {
        storage.remove(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
