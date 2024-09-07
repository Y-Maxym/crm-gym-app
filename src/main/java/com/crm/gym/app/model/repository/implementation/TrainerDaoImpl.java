package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class TrainerDaoImpl implements EntityDao<Long, Trainer> {

    private Storage<Long, Trainer> storage;

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
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
