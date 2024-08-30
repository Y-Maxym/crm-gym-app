package com.crm.gym.app.model.repository;

import com.crm.gym.app.model.entity.Trainer;

public interface TrainerDao {

    Trainer findById(Long id);

    void save(Trainer trainer);

    void update(Trainer trainer);
}
