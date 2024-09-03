package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.EntityDao;
import com.crm.gym.app.model.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TraineeDaoImpl implements EntityDao<Long, Trainee> {

    private final Storage<Long, Trainee> storage;

    @Override
    public Trainee findById(Long id) {
        return storage.get(id);
    }

    @Override
    public void save(Trainee trainee) {
        storage.put(trainee.getId(), trainee);
    }

    @Override
    public void update(Trainee trainee) {
        storage.put(trainee.getId(), trainee);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
