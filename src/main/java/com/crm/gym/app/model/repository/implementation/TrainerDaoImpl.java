package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainerDaoImpl implements EntityDao<Long, Trainer> {

    private final Storage<Long, Trainer> storage;

    @Override
    public Trainer findById(Long id) {
        return storage.get(id);
    }

    @Override
    public List<Trainer> findAll() {
        return storage.getAll();
    }

    @Override
    public void save(Trainer trainer) {
        storage.put(trainer.getId(), trainer);
    }

    @Override
    public void update(Trainer trainer) {
        storage.put(trainer.getId(), trainer);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
