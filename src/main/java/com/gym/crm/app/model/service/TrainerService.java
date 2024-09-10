package com.gym.crm.app.model.service;

import com.gym.crm.app.model.entity.Trainer;

public interface TrainerService {

    Trainer findById(Long id);

    void save(Trainer trainer);

    void update(Trainer trainer);
}
