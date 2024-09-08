package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Setter(onMethod_ = @Autowired)
public class TrainingDaoImpl implements EntityDao<Long, Training> {

    private Storage<Long, Training> storage;

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Training> findAll() {
        return storage.getAll();
    }

    @Override
    public Training save(Training training) {
        return storage.put(training);
    }

    @Override
    public Training update(Training training) {
        return storage.put(training);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
