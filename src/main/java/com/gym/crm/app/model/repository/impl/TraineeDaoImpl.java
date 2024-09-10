package com.gym.crm.app.model.repository.impl;

import com.gym.crm.app.model.entity.Trainee;
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
public class TraineeDaoImpl implements EntityDao<Long, Trainee> {

    private static AtomicLong currentId = new AtomicLong(0);

    private Storage storage;

    @Override
    public Optional<Trainee> findById(Long id) {
        Trainee entity = storage.get(id, Trainee.class);

        return Optional.ofNullable(entity);
    }

    @Override
    public List<Trainee> findAll() {
        return storage.getAll(Trainee.class);
    }

    @Override
    public Trainee saveOrUpdate(Trainee trainee) {
        if (isNull(trainee.getId())) {
            Long id = currentId.incrementAndGet();

            trainee = trainee.toBuilder().id(id).build();
        }

        return storage.put(trainee.getId(), trainee);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id, Trainee.class);
    }
}
