package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Training;

public abstract class TrainingRepository extends CrudRepository<Training, Long> {

    public TrainingRepository() {
        super(Training.class);
    }
}
