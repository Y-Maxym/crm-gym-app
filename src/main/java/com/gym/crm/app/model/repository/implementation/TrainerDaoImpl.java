package com.gym.crm.app.model.repository.implementation;

import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.repository.EntityDao;
import com.gym.crm.app.model.storage.Storage;
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
    public Trainer save(Trainer trainer) {
        return storage.put(trainer);
    }

    @Override
    public Trainer update(Trainer trainer) {
        return storage.put(trainer);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}