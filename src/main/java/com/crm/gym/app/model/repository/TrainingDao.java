package com.crm.gym.app.model.repository;

import com.crm.gym.app.model.entity.Training;

public interface TrainingDao {

    // TODO: by ?
    Training findById(Long id);

    // TODO: by ?
    void save(Training training);
}
