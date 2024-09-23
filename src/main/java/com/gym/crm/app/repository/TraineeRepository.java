package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;

import java.util.Optional;

public interface TraineeRepository extends CrudRepository<Trainee> {

    Optional<Trainee> findByUsername(String username);

    void deleteByUsername(String username);
}
