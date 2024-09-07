package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.User;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class UserDaoImpl implements EntityDao<Long, User> {

    private Storage<Long, User> storage;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<User> findAll() {
        return storage.getAll();
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
