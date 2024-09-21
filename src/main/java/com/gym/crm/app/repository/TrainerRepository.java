package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainer;

import java.util.List;
import java.util.Optional;

public abstract class TrainerRepository extends CrudRepository<Trainer, Long> {

    public TrainerRepository() {
        super(Trainer.class);
    }

    public abstract Optional<Trainer> findByUsername(String username);

    public abstract List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);
}
