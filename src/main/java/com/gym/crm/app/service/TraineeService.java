package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainee;

public interface TraineeService {

    Trainee findById(Long id);

    Trainee findByUsername(String username);

    void save(Trainee trainee);

    void update(Trainee trainee);

    void deleteById(Long id);

    void deleteByUsername(String username);
}
