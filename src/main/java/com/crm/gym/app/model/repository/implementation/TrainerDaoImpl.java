package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainer;
import com.crm.gym.app.model.repository.TrainerDao;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDaoImpl implements TrainerDao {

    @Override
    public Trainer findById(Long id) {
        return null;
    }

    @Override
    public void save(Trainer trainer) {

    }

    @Override
    public void update(Trainer trainer) {

    }
}
