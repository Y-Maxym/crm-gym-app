package com.gym.crm.app.facade;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingType;
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
import com.gym.crm.app.mapper.TrainingTypeMapper;
import com.gym.crm.app.mapper.UpdateTraineeProfileMapper;
import com.gym.crm.app.mapper.UpdateTrainerProfileMapper;
import com.gym.crm.app.repository.TrainingTypeRepository;
import com.gym.crm.app.rest.model.AddTrainingRequest;
import com.gym.crm.app.rest.model.ChangePasswordRequest;
import com.gym.crm.app.rest.model.GetTraineeProfileResponse;
import com.gym.crm.app.rest.model.GetTraineeTrainingsResponse;
import com.gym.crm.app.rest.model.GetTrainerProfileResponse;
import com.gym.crm.app.rest.model.GetTrainerTrainingsResponse;
import com.gym.crm.app.rest.model.GetTrainingTypeResponse;
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
import com.gym.crm.app.service.common.AuthService;
import com.gym.crm.app.service.common.BindingResultsService;
import com.gym.crm.app.service.common.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceFacade {

    private final TrainingTypeRepository trainingTypeRepository;

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
    private final TrainingTypeMapper trainingTypeMapper;
    private final AuthService authService;

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
    public UpdateTrainerProfileResponse updateTrainerProfile(String username, UpdateTrainerProfileRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainer update error");

        Trainer trainer = trainerService.findByUsername(username);
        trainer = updateTrainerProfileMapper.updateTraineeProfileFromDto(request, trainer);

        trainer = trainerService.update(trainer);

        return updateTrainerProfileMapper.map(trainer);
    }

    @Transactional
    public UpdateTraineeProfileResponse updateTraineeProfile(String username, UpdateTraineeProfileRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainee update error");

        Trainee trainee = traineeService.findByUsername(username);
        trainee = updateTraineeProfileMapper.updateTraineeProfileFromDto(request, trainee);

        trainee = traineeService.update(trainee);

        return updateTraineeProfileMapper.map(trainee);
    }

    @Transactional
    public void activateProfile(String username) {
        User user = userService.findByUsername(username);

        if (!user.isActive()) {
            user = user.toBuilder().isActive(true).build();

            userService.update(user);
        }
    }

    @Transactional
    public void deactivateProfile(String username) {
        User user = userService.findByUsername(username);

        if (user.isActive()) {
            user = user.toBuilder().isActive(false).build();

            userService.update(user);
        }
    }

    @Transactional
    public void deleteTraineeProfileByUsername(String username) {
        traineeService.deleteByUsername(username);
    }

    public List<GetTraineeTrainingsResponse> getTraineeTrainingsByCriteria(String username, LocalDate from, LocalDate to, String trainerName, String trainingType) {
        traineeService.findByUsername(username);

        List<Training> trainings = traineeService.findTrainingsByCriteria(username, from, to, trainerName, trainingType);

        return trainings.stream()
                .map(getTraineeTrainingsMapper::map)
                .toList();
    }

    public List<GetTrainerTrainingsResponse> getTrainerTrainingsByCriteria(String username, LocalDate from, LocalDate to, String traineeName, String trainingType) {
        trainerService.findByUsername(username);

        List<Training> trainings = trainerService.findTrainingsByCriteria(username, from, to, traineeName, trainingType);

        return trainings.stream()
                .map(getTrainerTrainingsMapper::map)
                .toList();
    }

    @Transactional
    public void addTraining(AddTrainingRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Training creation error");

        Trainee trainee = traineeService.findByUsername(request.getTraineeUsername());
        Trainer trainer = trainerService.findByUsername(request.getTrainerUsername());

        trainee.getTrainers().add(trainer);

        Training training = addTrainingMapper.map(request);

        training = training.toBuilder().trainingType(trainer.getSpecialization()).build();

        trainingService.save(training);
    }

    public List<TrainerProfileWithUsername> getTrainersNotAssignedByTraineeUsername(String username) {
        traineeService.findByUsername(username);

        List<Trainer> trainers = trainerService.getTrainersNotAssignedByTraineeUsername(username);

        return trainers.stream()
                .map(trainerProfileMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TrainerProfileWithUsername> updateTraineesTrainerList(String username, List<TrainerProfileOnlyUsername> request) {
        Trainee trainee = traineeService.findByUsername(username);
        List<Trainer> trainerList = request.stream()
                .map(trainer -> trainerService.findByUsername(trainer.getUsername()))
                .toList();

        trainee.getTrainers().clear();
        trainee.getTrainers().addAll(trainerList);
        traineeService.update(trainee);

        return trainee.getTrainers().stream()
                .map(trainerProfileMapper::map)
                .toList();
    }

    public List<GetTrainingTypeResponse> getTrainingTypes() {
        List<TrainingType> trainingTypes = trainingTypeRepository.findAll();

        return trainingTypes.stream()
                .map(trainingTypeMapper::mapToTrainingTypeResponse)
                .toList();
    }

    public User authenticate(UserCredentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        return authService.authenticate(username, password);
    }


    @Transactional
    public void changePassword(ChangePasswordRequest request, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Password change error");

        String username = request.getUsername();

        User user = userService.findByUsername(username);

        String hashedPassword = userProfileService.hashPassword(request.getNewPassword());
        user = user.toBuilder().password(hashedPassword).build();

        userService.update(user);
    }
}
