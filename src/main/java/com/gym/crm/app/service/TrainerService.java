package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TrainerService {

    Trainer findById(Long id);

    Trainer findByUsername(String username);

    Set<Training> findTrainingsByCriteria(String username, LocalDate from, LocalDate to,
                                          String traineeName, String trainingType);

    List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);

    void save(Trainer trainer);

    void update(Trainer trainer);
}
