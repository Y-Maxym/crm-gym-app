package com.gym.crm.app.service;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService {

    Trainee findById(Long id);

    Trainee findByUsername(String username);

    List<Training> findTrainingsByCriteria(String username, LocalDate from, LocalDate to,
                                           String trainerName, String trainingType);

    void save(Trainee trainee);

    Trainee update(Trainee trainee);

    void deleteById(Long id);

    void deleteByUsername(String username);
}
