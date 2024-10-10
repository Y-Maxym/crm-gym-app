package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Training;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineeRepository extends CrudRepository<Trainee> {

    Optional<Trainee> findByUsername(String username);

    List<Training> findTrainingsByCriteria(@NotNull String username, LocalDate from, LocalDate to, String trainerName,
                                           String trainingType);

    void deleteByUsername(String username);
}
