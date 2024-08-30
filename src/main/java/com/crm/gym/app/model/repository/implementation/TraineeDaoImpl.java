package com.crm.gym.app.model.repository.implementation;

import com.crm.gym.app.model.entity.Trainee;
import com.crm.gym.app.model.repository.TraineeDao;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    @Override
    public Trainee findById(Long id) {
        return null;
    }

    @Override
    public void save(Trainee trainee) {

    }

    @Override
    public void update(Trainee trainee) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
