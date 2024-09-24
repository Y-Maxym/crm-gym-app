package com.gym.crm.app.repository;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrainerRepository extends CrudRepository<Trainer> {

    Optional<Trainer> findByUsername(String username);

    Set<Training> findTrainingsByCriteria(@NotNull String username, LocalDate from, LocalDate to, String traineeName,
                                          String trainingType);

    List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);
}
