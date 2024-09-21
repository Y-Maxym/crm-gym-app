package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;

import java.util.Optional;

public abstract class TraineeRepository extends CrudRepository<Trainee, Long> {

    public TraineeRepository() {
        super(Trainee.class);
    }

    public abstract Optional<Trainee> findByUsername(String username);

    public abstract void deleteByUsername(String username);
}
