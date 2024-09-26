package com.gym.crm.app.facade;

import com.gym.crm.app.dto.AuthCredentials;
import com.gym.crm.app.dto.request.CreateTraineeProfileRequest;
import com.gym.crm.app.dto.request.CreateTrainerProfileRequest;
import com.gym.crm.app.dto.request.TraineeProfileRequest;
import com.gym.crm.app.dto.request.TrainerProfileRequest;
import com.gym.crm.app.dto.request.TrainingRequest;
import com.gym.crm.app.dto.response.CreateTraineeProfileResponse;
import com.gym.crm.app.dto.response.CreateTrainerProfileResponse;
import com.gym.crm.app.dto.response.TraineeProfileResponse;
import com.gym.crm.app.dto.response.TrainerProfileResponse;
import com.gym.crm.app.dto.response.TrainingResponse;
import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.EntityPersistException;
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
import com.gym.crm.app.service.common.BindingResultsService;
import com.gym.crm.app.service.common.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    private final BindingResultsService bindingResultsService;

    private final TraineeProfileMapper traineeProfileMapper;
    private final TrainerProfileMapper trainerProfileMapper;
    private final TrainingMapper trainingMapper;
    private final CreateTraineeProfileMapper createTraineeProfileMapper;
    private final CreateTrainerProfileMapper createTrainerProfileMapper;

    @Transactional
    public CreateTrainerProfileResponse createTrainerProfile(CreateTrainerProfileRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainer creation error");

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
    public CreateTraineeProfileResponse createTraineeProfile(CreateTraineeProfileRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainee creation error");

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

        Trainer trainer = trainerService.findByUsername(user.getUsername());

        return trainerProfileMapper.map(trainer);
    }

    public TraineeProfileResponse findTraineeProfileByUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        Trainee trainee = traineeService.findByUsername(user.getUsername());

        return traineeProfileMapper.map(trainee);
    }

    @Transactional
    public void changePassword(String changedPassword, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        String hashedPassword = userProfileService.hashPassword(changedPassword);
        user = user.toBuilder().password(hashedPassword).build();

        userService.update(user);
    }

    @Transactional
    public void updateTrainerProfile(TrainerProfileRequest request, BindingResult bindingResult, AuthCredentials credentials) {
        authService.authenticate(credentials);

        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainer update error");

        Trainer updatedTrainer = trainerProfileMapper.map(request);

        String username = credentials.username();

        Trainer trainer = trainerService.findByUsername(username);
        User user = trainer.getUser();

        user = updateUserProfile(updatedTrainer.getUser(), user);

        trainer = updatedTrainer.toBuilder().id(trainer.getId()).user(user).build();
        trainerService.update(trainer);
    }

    @Transactional
    public void updateTraineeProfile(TraineeProfileRequest request, BindingResult bindingResult, AuthCredentials credentials) {
        Trainee updatedTrainee = traineeProfileMapper.map(request);

        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainee update error");

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

        if (!user.isActive()) {
            user = user.toBuilder().isActive(true).build();

            userService.update(user);
        }
    }

    @Transactional
    public void deactivateProfile(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        if (user.isActive()) {
            user = user.toBuilder().isActive(false).build();

            userService.update(user);
        }
    }

    @Transactional
    public void deleteTraineeProfileByUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        traineeService.deleteByUsername(user.getUsername());
    }

    public Set<TrainingResponse> getTraineeTrainingsByCriteria(LocalDate from, LocalDate to, String trainerName,
                                                               String trainingType, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        Set<Training> trainings = traineeService.findTrainingsByCriteria(user.getUsername(), from, to, trainerName, trainingType);

        return trainingMapper.mapList(trainings);
    }

    public Set<TrainingResponse> getTrainerTrainingsByCriteria(LocalDate from, LocalDate to, String traineeName,
                                                               String trainingType, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        Set<Training> trainings = trainerService.findTrainingsByCriteria(user.getUsername(), from, to, traineeName, trainingType);

        return trainingMapper.mapList(trainings);
    }

    @Transactional
    public void addTraining(TrainingRequest request, BindingResult bindingResult, AuthCredentials credentials) {
        authService.authenticate(credentials);

        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Training creation error");

        Training training = trainingMapper.map(request);

        trainingService.save(training);
    }

    public List<TrainerProfileResponse> getTrainersNotAssignedByTraineeUsername(AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        List<Trainer> trainers = trainerService.getTrainersNotAssignedByTraineeUsername(user.getUsername());

        return trainerProfileMapper.mapList(trainers);
    }

    @Transactional
    public void addTrainerToTrainee(String trainerUsername, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        Trainee trainee = traineeService.findByUsername(user.getUsername());
        Trainer trainer = trainerService.findByUsername(trainerUsername);

        trainee.getTrainers().add(trainer);
        traineeService.update(trainee);
    }

    @Transactional
    public void removeTraineesTrainer(String trainerUsername, AuthCredentials credentials) {
        User user = authService.authenticate(credentials);

        Trainee trainee = traineeService.findByUsername(user.getUsername());
        Trainer trainer = trainerService.findByUsername(trainerUsername);

        trainee.getTrainers().remove(trainer);
        traineeService.update(trainee);
    }

    private User updateUserProfile(User updatedUser, User user) {
        String firstName = updatedUser.getFirstName();
        String lastName = updatedUser.getLastName();
        String updatedUsername = userProfileService.generateUsername(firstName, lastName);

        return user.toBuilder().firstName(firstName).lastName(lastName).username(updatedUsername).build();
    }
}
