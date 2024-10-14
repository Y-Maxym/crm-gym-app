package com.gym.crm.app.facade;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.exception.AuthenticationException;
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
import com.gym.crm.app.rest.model.ActivateDeactivateProfileRequest;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.gym.crm.app.rest.exception.ErrorCode.ACTIVATE_DEACTIVATE_PROFILE_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.AUTHENTICATION_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.INVALID_USERNAME;
import static com.gym.crm.app.rest.exception.ErrorCode.INVALID_USERNAME_OR_PASSWORD;
import static com.gym.crm.app.rest.exception.ErrorCode.PASSWORD_CHANGE_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINEE_CREATE_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINEE_UPDATE_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINER_CREATE_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINER_UPDATE_ERROR;
import static com.gym.crm.app.rest.exception.ErrorCode.TRAINING_CREATE_ERROR;

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
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainer creation error", TRAINER_CREATE_ERROR.getCode());

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
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainee creation error", TRAINEE_CREATE_ERROR.getCode());

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
    public UpdateTrainerProfileResponse updateTrainerProfile(String username, UpdateTrainerProfileRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainer update error", TRAINER_UPDATE_ERROR.getCode());
        checkUsername(username, httpServletRequest);

        Trainer trainer = trainerService.findByUsername(username);
        trainer = updateTrainerProfileMapper.updateTraineeProfileFromDto(request, trainer);

        trainer = trainerService.update(trainer);

        return updateTrainerProfileMapper.map(trainer);
    }

    @Transactional
    public UpdateTraineeProfileResponse updateTraineeProfile(String username, UpdateTraineeProfileRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Trainee update error", TRAINEE_UPDATE_ERROR.getCode());
        checkUsername(username, httpServletRequest);

        Trainee trainee = traineeService.findByUsername(username);
        trainee = updateTraineeProfileMapper.updateTraineeProfileFromDto(request, trainee);

        trainee = traineeService.update(trainee);

        return updateTraineeProfileMapper.map(trainee);
    }

    @Transactional
    public void activateDeactivateProfile(String username, ActivateDeactivateProfileRequest request, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Activate/Deactivate profile error", ACTIVATE_DEACTIVATE_PROFILE_ERROR.getCode());
        checkUsername(username, httpServletRequest);

        User user = userService.findByUsername(username);

        if ((user.isActive() && request.getIsActive())
                || (!user.isActive() && !request.getIsActive())) {
            return;
        }

        user = user.toBuilder().isActive(request.getIsActive()).build();
        userService.update(user);
    }

    @Transactional
    public void deleteTraineeProfileByUsername(String username, HttpServletRequest httpServletRequest) {
        checkUsername(username, httpServletRequest);

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
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Training creation error", TRAINING_CREATE_ERROR.getCode());

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
    public List<TrainerProfileWithUsername> updateTraineesTrainerList(String username, List<TrainerProfileOnlyUsername> request, HttpServletRequest httpServletRequest) {
        checkUsername(username, httpServletRequest);

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

    public User authenticate(UserCredentials credentials, BindingResult bindingResult) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Authentication error", AUTHENTICATION_ERROR.getCode());

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        return authService.authenticate(username, password);
    }


    @Transactional
    public void changePassword(ChangePasswordRequest request, BindingResult bindingResult, HttpServletRequest httpRequest) {
        bindingResultsService.handle(bindingResult, EntityPersistException::new, "Password change error", PASSWORD_CHANGE_ERROR.getCode());

        String username = request.getUsername();
        String password = request.getPassword();
        checkUsernamePassword(username, password, httpRequest);

        User user = userService.findByUsername(username);

        String hashedPassword = userProfileService.hashPassword(request.getNewPassword());
        user = user.toBuilder().password(hashedPassword).build();

        userService.update(user);
    }

    private void checkUsernamePassword(String username, String password, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (!sessionUser.getUsername().equals(username)
                || !userProfileService.isPasswordCorrect(password, sessionUser.getPassword())) {
            throw new AuthenticationException("Invalid username or password", INVALID_USERNAME_OR_PASSWORD.getCode());
        }
    }

    private void checkUsername(String username, HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (!sessionUser.getUsername().equals(username)) {
            throw new AuthenticationException("Invalid username", INVALID_USERNAME.getCode());
        }
    }
}
