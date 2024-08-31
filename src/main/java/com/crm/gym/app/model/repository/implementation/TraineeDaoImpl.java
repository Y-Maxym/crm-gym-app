package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.TraineeDao;
import com.crm.gym.app.model.storage.implementation.TraineeStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TraineeDaoImpl implements TraineeDao {

    private final TraineeStorage storage;

    @Override
    public Trainee findById(Long id) {
        return storage.get(id);
    }

    @Override
    public void save(Trainee trainee) {
        storage.put(trainee.getUserId(), trainee);
    }

    @Override
    public void update(Trainee trainee) {
        storage.put(trainee.getUserId(), trainee);
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }
}
