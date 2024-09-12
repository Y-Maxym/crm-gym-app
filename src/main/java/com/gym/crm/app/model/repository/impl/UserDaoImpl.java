package com.gym.crm.app.model.repository.impl;

import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.isNull;

@Repository
@Setter(onMethod_ = @Autowired)
public class UserDaoImpl implements EntityDao<Long, User> {

    private static AtomicLong currentId = new AtomicLong(0);

    private Storage storage;

    @Override
    public Optional<User> findById(Long id) {
        User entity = storage.get(id, User.class);

        return Optional.ofNullable(entity);
    }

    @Override
    public List<User> findAll() {
        return storage.getAll(User.class);
    }

    @Override
    public User save(User user) {
        if (isNull(user.getId())) {
            Long id = currentId.incrementAndGet();

            user = user.toBuilder().id(id).build();
        }

        return storage.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        return storage.put(user.getId(), user);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id, User.class);
    }
}
