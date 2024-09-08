package com.gym.crm.app.model.service;

import com.gym.crm.app.model.entity.Training;

public interface TrainingService {

    Training findById(Long id);

    void save(Training training);
}
