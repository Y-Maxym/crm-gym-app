package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Training;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrainingDaoImpl implements EntityDao<Long, Training> {

    private final Storage<Long, Training> storage;

    @Override
    public Training findById(Long id) {
        return storage.get(id);
    }

    @Override
    public List<Training> findAll() {
        return storage.getAll();
    }

    @Override
    public void save(Training training) {
        storage.put(training.getId(), training);
    }

    @Override
    public void update(Training training) {
        storage.put(training.getId(), training);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
