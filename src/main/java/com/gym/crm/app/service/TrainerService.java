package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer findById(Long id);

    Trainer findByUsername(String username);

    List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);

    void save(Trainer trainer);

    Trainer update(Trainer trainer);
}
