package com.gym.crm.app.facade;

import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.entity.User;
import com.gym.crm.app.model.service.TraineeService;
import com.gym.crm.app.model.service.TrainerService;
import com.gym.crm.app.model.service.TrainingService;
import com.gym.crm.app.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;

    public Trainee findTraineeById(Long id) {
        return traineeService.findById(id);
    }

    public void saveTrainee(Trainee trainee) {
        traineeService.save(trainee);
    }

    public void updateTrainee(Trainee trainee) {
        traineeService.update(trainee);
    }

    public void deleteTraineeById(Long id) {
        traineeService.deleteById(id);
    }

    public Trainer findTrainerById(Long id) {
        return trainerService.findById(id);
    }

    public void saveTrainer(Trainer trainer) {
        trainerService.save(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerService.update(trainer);
    }

    public Training findTrainingById(Long id) {
        return trainingService.findById(id);
    }

    public void saveTraining(Training training) {
        trainingService.save(training);
    }

    public User findUserById(Long id) {
        return userService.findById(id);
    }

    public void saveUser(User user) {
        userService.save(user);
    }

    public void updateUser(User user) {
        userService.update(user);
    }

    public void deleteUser(Long id) {
        userService.deleteById(id);
    }
}
