package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.repository.EntityDao;
import com.gym.crm.app.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.isNull;

@Repository
@Setter(onMethod_ = @Autowired)
public class TrainingDaoImpl implements EntityDao<Long, Training> {

    private static final AtomicLong currentId = new AtomicLong(0);

    private Storage storage;

    @Override
    public Optional<Training> findById(Long id) {
        Training entity = storage.get(id, Training.class);

        return Optional.ofNullable(entity);
    }

    @Override
    public List<Training> findAll() {
        return storage.getAll(Training.class);
    }

    @Override
    public Training save(Training training) {
        if (isNull(training.getId())) {
            Long id = currentId.incrementAndGet();

            training = training.toBuilder().id(id).build();
        }

        return storage.put(training.getId(), training);
    }

    @Override
    public Training update(Training training) {
        return storage.put(training.getId(), training);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id, Training.class);
    }
}
