package com.crm.gym.app.model.service;

import com.crm.gym.app.model.entity.Trainer;

public interface TrainerService {

    Trainer findById(Long id);

    void save(Trainer trainer);

    void update(Trainer trainer);
}
