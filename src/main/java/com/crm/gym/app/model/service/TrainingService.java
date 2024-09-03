package com.crm.gym.app.model.service;

import com.crm.gym.app.model.entity.Training;

public interface TrainingService {

    Training findById(Long id);

    void save(Training training);
}
