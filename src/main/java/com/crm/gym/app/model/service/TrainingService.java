package com.crm.gym.app.model.service;

import com.crm.gym.app.model.entity.Training;

public interface TrainingService {

    // by ?
    Training find(int id);

    void save(Training training);
}
