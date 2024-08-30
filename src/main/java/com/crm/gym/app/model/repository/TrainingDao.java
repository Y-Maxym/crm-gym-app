package com.crm.gym.app.model.repository;

import com.crm.gym.app.model.entity.Training;

public interface TrainingDao {

    Training findById(Long id);

    void save(Training training);
}
