package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainer;

public interface TrainerService {

    Trainer findById(Long id);

    void save(Trainer trainer);

    void update(Trainer trainer);
}
