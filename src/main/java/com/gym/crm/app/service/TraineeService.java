package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainee;

public interface TraineeService {

    Trainee findById(Long id);

    void save(Trainee trainee);

    void update(Trainee trainee);

    void deleteById(Long id);
}
