package com.gym.crm.app.model.repository.impl;

import com.gym.crm.app.model.entity.Trainer;
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
public class TrainerDaoImpl implements EntityDao<Long, Trainer> {

    private static AtomicLong currentId = new AtomicLong(0);

    private Storage storage;

    @Override
    public Optional<Trainer> findById(Long id) {
        Trainer entity = storage.get(id, Trainer.class);

        return Optional.ofNullable(entity);
    }

    @Override
    public List<Trainer> findAll() {
        return storage.getAll(Trainer.class);
    }

    @Override
    public Trainer saveOrUpdate(Trainer trainer) {
        if (isNull(trainer.getId())) {
            Long id = currentId.incrementAndGet();

            trainer = trainer.toBuilder().id(id).build();
        }

        return storage.put(trainer.getId(), trainer);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id, Trainer.class);
    }
}