package com.gym.crm.app.facade;

import com.gym.crm.app.dto.AuthCredentials;
import com.gym.crm.app.dto.request.trainee.CreateTraineeProfileRequest;
import com.gym.crm.app.dto.request.trainee.TraineeProfileRequest;
import com.gym.crm.app.dto.request.trainer.CreateTrainerProfileRequest;
import com.gym.crm.app.dto.request.trainer.TrainerProfileRequest;
import com.gym.crm.app.dto.request.training.TrainingRequest;
import com.gym.crm.app.dto.response.trainee.CreateTraineeProfileResponse;
import com.gym.crm.app.dto.response.trainee.TraineeProfileResponse;
import com.gym.crm.app.dto.response.trainer.CreateTrainerProfileResponse;
import com.gym.crm.app.dto.response.trainer.TrainerProfileResponse;
import com.gym.crm.app.dto.response.training.TrainingResponse;
import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.mapper.CreateTraineeProfileMapper;
import com.gym.crm.app.mapper.CreateTrainerProfileMapper;
import com.gym.crm.app.mapper.TraineeProfileMapper;
import com.gym.crm.app.mapper.TrainerProfileMapper;
import com.gym.crm.app.mapper.TrainingMapper;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.TrainerService;
import com.gym.crm.app.service.TrainingService;
import com.gym.crm.app.service.UserService;
import com.gym.crm.app.service.common.AuthService;
import com.gym.crm.app.service.common.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final AuthService authService;

    private final TraineeProfileMapper traineeProfileMapper;
    private final TrainerProfileMapper trainerProfileMapper;
    private final TrainingMapper trainingMapper;
    private final CreateTraineeProfileMapper createTraineeProfileMapper;
    private final CreateTrainerProfileMapper createTrainerProfileMapper;

    @Transactional
    public CreateTrainerProfileResponse createTrainerProfile(CreateTrainerProfileRequest request) {
        Trainer trainer = createTrainerProfileMapper.map(request);

        String password = userProfileService.generatePassword();

        User user = trainer.getUser();
        user = userService.prepareUserForSave(user, password);

        trainer = trainer.toBuilder().user(user).build();
        trainerService.save(trainer);

        user = user.toBuilder().password(password).build();
        trainer = trainer.toBuilder().user(user).build();

        return createTrainerProfileMapper.map(trainer);
    }

    @Transactional
    public CreateTraineeProfileResponse createTraineeProfile(CreateTraineeProfileRequest request) {
        Trainee trainee = createTraineeProfileMapper.map(request);

        String password = userProfileService.generatePassword();

        User user = trainee.getUser();
        user = userService.prepareUserForSave(user, password);

        trainee = trainee.toBuilder().user(user).build();
        traineeService.save(trainee);

        user = user.toBuilder().password(password).build();
        trainee = trainee.toBuilder().user(user).build();

        return createTraineeProfileMapper.map(trainee);
    }

    public TrainerProfileResponse findTrainerProfileByUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        String username = user.getUsername();
        Trainer trainer = trainerService.findByUsername(username);

        return trainerProfileMapper.map(trainer);

    }

    public TraineeProfileResponse findTraineeProfileByUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        String username = user.getUsername();
        Trainee trainee = traineeService.findByUsername(username);

        return traineeProfileMapper.map(trainee);

    }

    @Transactional
    public void changePassword(String changedPassword, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);
        user = user.toBuilder().password(changedPassword).build();

        userService.update(user);
    }

    @Transactional
    public void updateTrainerProfile(TrainerProfileRequest request, AuthCredentials credentials) {
        authService.authenticate(credentials);

        Trainer updatedTrainer = trainerProfileMapper.map(request);

        String username = credentials.username();

        Trainer trainer = trainerService.findByUsername(username);
        User user = trainer.getUser();

        user = updateUserProfile(updatedTrainer.getUser(), user);

        trainer = updatedTrainer.toBuilder().id(trainer.getId()).user(user).build();
        trainerService.update(trainer);
    }

    @Transactional
    public void updateTraineeProfile(TraineeProfileRequest request, AuthCredentials credentials) {
        Trainee updatedTrainee = traineeProfileMapper.map(request);

        authService.authenticate(credentials);

        String username = credentials.username();

        Trainee trainee = traineeService.findByUsername(username);
        User user = trainee.getUser();

        user = updateUserProfile(updatedTrainee.getUser(), user);

        trainee = updatedTrainee.toBuilder().id(trainee.getId()).user(user).build();
        traineeService.update(trainee);
    }

    @Transactional
    public void activateProfile(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);
        user = user.toBuilder().isActive(true).build();

        userService.update(user);
    }

    @Transactional
    public void deactivateProfile(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);
        user = user.toBuilder().isActive(false).build();

        userService.update(user);
    }

    @Transactional
    public void deleteTraineeProfileByUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        traineeService.deleteByUsername(user.getUsername());
    }

    public Set<TrainingResponse> getTraineeTrainingsByCriteria(LocalDate from, LocalDate to, String trainerName,
                                                               String trainingType, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        String username = user.getUsername();
        Trainee trainee = traineeService.findByUsername(username);

        Set<Training> trainings = filterTrainings(trainee.getTrainings(), from, to, trainerName, trainingType);

        return trainingMapper.mapList(trainings);
    }

    public Set<TrainingResponse> getTrainerTrainingsByCriteria(LocalDate from, LocalDate to, String trainerName,
                                                               String trainingType, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        String username = user.getUsername();
        Trainer trainer = trainerService.findByUsername(username);

        Set<Training> trainings = filterTrainings(trainer.getTrainings(), from, to, trainerName, trainingType);

        return trainingMapper.mapList(trainings);
    }

    @Transactional
    public void addTraining(TrainingRequest request, AuthCredentials credentials) {
        authService.authenticate(credentials);

        Training training = trainingMapper.map(request);

        trainingService.save(training);
    }

    public List<TrainerProfileResponse> getTrainersNotAssignedByTraineeUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);
        String username = user.getUsername();

        List<Trainer> trainers = trainerService.getTrainersNotAssignedByTraineeUsername(username);

        return trainerProfileMapper.mapList(trainers);
    }

    private Set<Training> filterTrainings(Set<Training> trainings, LocalDate from, LocalDate to, String
            trainerName, String trainingType) {
        return trainings.stream()
                .filter(training -> isNull(from) || training.getTrainingDate().isAfter(from))
                .filter(training -> isNull(to) || training.getTrainingDate().isBefore(to))
                .filter(training -> isNull(trainerName) || training.getTrainer().getUser().getFirstName().equals(trainerName))
                .filter(training -> isNull(trainingType) || training.getTrainingType().getTrainingTypeName().equals(trainingType))
                .collect(Collectors.toSet());
    }

    private User updateUserProfile(User updatedUser, User user) {
        String firstName = updatedUser.getFirstName();
        String lastName = updatedUser.getLastName();
        String updatedUsername = userProfileService.generateUsername(firstName, lastName);

        return user.toBuilder().firstName(firstName).lastName(lastName).username(updatedUsername).build();
    }
}
