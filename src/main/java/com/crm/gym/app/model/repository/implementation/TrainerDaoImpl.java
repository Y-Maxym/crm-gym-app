package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.TrainerDao;
import com.crm.gym.app.model.storage.implementation.TrainerStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrainerDaoImpl implements TrainerDao {

    private final TrainerStorage storage;

    @Override
    public Trainer findById(Long id) {
        return storage.get(id);
    }

    @Override
    public void save(Trainer trainer) {
        storage.put(trainer.getUserId(), trainer);
    }

    @Override
    public void update(Trainer trainer) {
        storage.put(trainer.getUserId(), trainer);
    }
}
