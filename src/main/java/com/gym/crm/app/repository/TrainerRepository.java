package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends CrudRepository<Trainer> {

    Optional<Trainer> findByUsername(String username);

    List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);
}
