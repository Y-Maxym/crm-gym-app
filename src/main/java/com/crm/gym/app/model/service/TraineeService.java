package com.crm.gym.app.model.service;

import com.crm.gym.app.model.entity.Trainee;

public interface TraineeService {

    Trainee findById(Long id);

    void save(Trainee trainee);

    void update(Trainee trainee);

    void deleteById(Long id);
}
