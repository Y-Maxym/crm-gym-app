package com.crm.gym.app.model.repository;

import com.crm.gym.app.model.entity.Trainee;

public interface TraineeDao {

    Trainee findById(Long id);

    void save(Trainee trainee);

    void update(Trainee trainee);

    void deleteById(Long id);
}
