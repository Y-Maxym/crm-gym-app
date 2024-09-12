package com.gym.crm.app.service;

import com.gym.crm.app.entity.Training;

public interface TrainingService {

    Training findById(Long id);

    void save(Training training);
}
