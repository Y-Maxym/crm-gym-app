package com.gym.crm.app.service;

import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingSearchFilter;

import java.util.List;

public interface TrainingService {

    Training findById(Long id);

    List<Training> findTraineeTrainingByCriteria(TrainingSearchFilter searchFilter);

    List<Training> findTrainerTrainingByCriteria(TrainingSearchFilter searchFilter);

    void save(Training training);

}
