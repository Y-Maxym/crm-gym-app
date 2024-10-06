package com.gym.crm.app.facade;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.EntityPersistException;
import com.gym.crm.app.mapper.AddTrainingMapper;
import com.gym.crm.app.mapper.CreateTraineeProfileMapper;
import com.gym.crm.app.mapper.CreateTrainerProfileMapper;
import com.gym.crm.app.mapper.GetTraineeProfileMapper;
import com.gym.crm.app.mapper.GetTraineeTrainingsMapper;
import com.gym.crm.app.mapper.GetTrainerProfileMapper;
import com.gym.crm.app.mapper.GetTrainerTrainingsMapper;
import com.gym.crm.app.mapper.TrainerProfileMapper;
import com.gym.crm.app.mapper.UpdateTraineeProfileMapper;
import com.gym.crm.app.mapper.UpdateTrainerProfileMapper;
import com.gym.crm.app.rest.model.AddTrainingRequest;
import com.gym.crm.app.rest.model.GetTraineeProfileResponse;
import com.gym.crm.app.rest.model.GetTraineeTrainingsResponse;
import com.gym.crm.app.rest.model.GetTrainerProfileResponse;
import com.gym.crm.app.rest.model.GetTrainerTrainingsResponse;
import com.gym.crm.app.rest.model.LoginChangeRequest;
import com.gym.crm.app.rest.model.TraineeCreateRequest;
import com.gym.crm.app.rest.model.TrainerCreateRequest;
import com.gym.crm.app.rest.model.TrainerProfileOnlyUsername;
import com.gym.crm.app.rest.model.TrainerProfileWithUsername;
import com.gym.crm.app.rest.model.UpdateTraineeProfileRequest;
import com.gym.crm.app.rest.model.UpdateTraineeProfileResponse;
import com.gym.crm.app.rest.model.UpdateTrainerProfileRequest;
import com.gym.crm.app.rest.model.UpdateTrainerProfileResponse;
import com.gym.crm.app.rest.model.UserCredentials;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.TrainerService;
import com.gym.crm.app.service.TrainingService;
import com.gym.crm.app.service.UserService;
import com.gym.crm.app.service.common.BindingResultsService;
import com.gym.crm.app.service.common.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final BindingResultsService bindingResultsService;

    private final TrainerProfileMapper trainerProfileMapper;
    private final AddTrainingMapper addTrainingMapper;
    private final CreateTraineeProfileMapper createTraineeProfileMapper;
    private final CreateTrainerProfileMapper createTrainerProfileMapper;
    private final GetTraineeProfileMapper getTraineeProfileMapper;
    private final GetTrainerProfileMapper getTrainerProfileMapper;
    private final UpdateTrainerProfileMapper updateTrainerProfileMapper;
    private final UpdateTraineeProfileMapper updateTraineeProfileMapper;
    private final GetTrainerTrainingsMapper getTrainerTrainingsMapper;
    private final GetTraineeTrainingsMapper getTraineeTrainingsMapper;

    @Transactional
    public UserCredentials createTrainerProfile(TrainerCreateRequest request, BindingResult bindingResult) {
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
    public UserCredentials createTraineeProfile(TraineeCreateRequest request, BindingResult bindingResult) {
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

    public GetTrainerProfileResponse findTrainerProfileByUsername(String username) {
        Trainer trainer = trainerService.findByUsername(username);

        return getTrainerProfileMapper.map(trainer);
    }

    public GetTraineeProfileResponse findTraineeProfileByUsername(String username) {
        Trainee trainee = traineeService.findByUsername(username);

        return getTraineeProfileMapper.map(trainee);
    }

    @Transactional
    public void changePassword(LoginChangeRequest request, User user) {
        String hashedPassword = userProfileService.hashPassword(request.getNewPassword());
        user = user.toBuilder().password(hashedPassword).build();

        userService.update(user);
    }

    @Transactional
    public UpdateTrainerProfileResponse updateTrainerProfile(UpdateTrainerProfileRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainer update error");

        Trainer updatedTrainer = updateTrainerProfileMapper.map(request);

        Trainer trainer = trainerService.findByUsername(request.getUsername());
        User user = trainer.getUser();

        user = updateUserProfile(updatedTrainer.getUser(), user);

        trainer = updatedTrainer.toBuilder().id(trainer.getId()).user(user).build();
        trainer = trainerService.update(trainer);

        return updateTrainerProfileMapper.map(trainer);
    }

    @Transactional
    public UpdateTraineeProfileResponse updateTraineeProfile(String username, UpdateTraineeProfileRequest request, BindingResult bindingResult) {
        Trainee updatedTrainee = updateTraineeProfileMapper.map(request);

        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainee update error");

        Trainee trainee = traineeService.findByUsername(username);
        User user = trainee.getUser();

        user = updateUserProfile(updatedTrainee.getUser(), user);

        trainee = updatedTrainee.toBuilder().id(trainee.getId()).user(user).build();
        trainee = traineeService.update(trainee);

        return updateTraineeProfileMapper.map(trainee);
    }

    @Transactional
    public void activateProfile(User user) {
        if (!user.isActive()) {
            user = user.toBuilder().isActive(true).build();

            userService.update(user);
        }
    }

    @Transactional
    public void deactivateProfile(User user) {
        if (user.isActive()) {
            user = user.toBuilder().isActive(false).build();

            userService.update(user);
        }
    }

    @Transactional
    public void deleteTraineeProfileByUsername(String username) {
        traineeService.deleteByUsername(username);
    }

    public Set<GetTraineeTrainingsResponse> getTraineeTrainingsByCriteria(String username, LocalDate from, LocalDate to, String trainerName, String trainingType) {
        Set<Training> trainings = traineeService.findTrainingsByCriteria(username, from, to, trainerName, trainingType);

        return trainings.stream()
                .map(getTraineeTrainingsMapper::map)
                .collect(Collectors.toSet());
    }

    public Set<GetTrainerTrainingsResponse> getTrainerTrainingsByCriteria(String username, LocalDate from, LocalDate to, String traineeName, String trainingType) {
        Set<Training> trainings = trainerService.findTrainingsByCriteria(username, from, to, traineeName, trainingType);

        return trainings.stream()
                .map(getTrainerTrainingsMapper::map)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void addTraining(AddTrainingRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Training creation error");

        Training training = addTrainingMapper.map(request);

        trainingService.save(training);
    }

    public List<TrainerProfileWithUsername> getTrainersNotAssignedByTraineeUsername(String username) {
        List<Trainer> trainers = trainerService.getTrainersNotAssignedByTraineeUsername(username);

        return trainers.stream()
                .map(trainerProfileMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTraineesTrainerList(String username, List<TrainerProfileOnlyUsername> request) {
        Trainee trainee = traineeService.findByUsername(username);
        List<Trainer> trainerList = request.stream()
                .map(trainer -> trainerService.findByUsername(trainer.getUsername()))
                .toList();

        trainee.getTrainers().clear();
        trainee.getTrainers().addAll(trainerList);
        traineeService.update(trainee);
    }

    private User updateUserProfile(User updatedUser, User user) {
        String firstName = updatedUser.getFirstName();
        String lastName = updatedUser.getLastName();
        String updatedUsername = userProfileService.generateUsername(firstName, lastName);

        return user.toBuilder().firstName(firstName).lastName(lastName).username(updatedUsername).build();
    }
}
