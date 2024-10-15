package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService {

    Trainer findById(Long id);

    Trainer findByUsername(String username);

    List<Training> findTrainingsByCriteria(String username, LocalDate from, LocalDate to,
                                           String traineeName, String trainingType);

    List<Trainer> getTrainersNotAssignedByTraineeUsername(String username);

    void save(Trainer trainer);

    Trainer update(Trainer trainer);
}
