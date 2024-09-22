package com.gym.crm.app.repository.impl;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.repository.TrainingRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingRepositoryImpl extends CrudRepositoryImpl<Training> implements TrainingRepository {

    public TrainingRepositoryImpl() {
        super(Training.class);
    }
}
