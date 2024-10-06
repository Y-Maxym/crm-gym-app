//package com.gym.crm.app.facade;
//
//import com.gym.crm.app.dto.AuthCredentials;
//import com.gym.crm.app.dto.request.CreateTraineeProfileRequest;
//import com.gym.crm.app.dto.request.CreateTrainerProfileRequest;
//import com.gym.crm.app.dto.request.TraineeProfileRequest;
//import com.gym.crm.app.dto.request.TrainerProfileRequest;
//import com.gym.crm.app.dto.request.TrainingRequest;
//import com.gym.crm.app.entity.Trainee;
//import com.gym.crm.app.entity.Trainer;
//import com.gym.crm.app.entity.User;
//import com.gym.crm.app.exception.AuthenticationException;
//import com.gym.crm.app.exception.EntityPersistException;
//import com.gym.crm.app.exception.EntityValidationException;
//import com.gym.crm.app.mapper.CreateTraineeProfileMapper;
//import com.gym.crm.app.mapper.CreateTrainerProfileMapper;
//import com.gym.crm.app.mapper.TraineeProfileMapper;
//import com.gym.crm.app.mapper.TrainerProfileMapper;
//import com.gym.crm.app.mapper.TrainingMapper;
//import com.gym.crm.app.service.TraineeService;
//import com.gym.crm.app.service.TrainerService;
//import com.gym.crm.app.service.TrainingService;
//import com.gym.crm.app.service.UserService;
//import com.gym.crm.app.service.common.AuthService;
//import com.gym.crm.app.service.common.BindingResultsService;
//import com.gym.crm.app.service.common.UserProfileService;
//import com.gym.crm.app.utils.EntityTestData;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.BindingResult;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class ServiceFacadeTest {
//
//    @Mock
//    private TraineeService traineeService;
//
//    @Mock
//    private TrainerService trainerService;
//
//    @Mock
//    private TrainingService trainingService;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private UserProfileService userProfileService;
//
//    @Mock
//    private AuthService authService;
//
//    @Mock
//    private BindingResultsService bindingResultsService;
//
//    @Mock
//    private TraineeProfileMapper traineeProfileMapper;
//
//    @Mock
//    private TrainerProfileMapper trainerProfileMapper;
//
//    @Mock
//    private TrainingMapper trainingMapper;
//
//    @Mock
//    private CreateTraineeProfileMapper createTraineeProfileMapper;
//
//    @Mock
//    private CreateTrainerProfileMapper createTrainerProfileMapper;
//
//    @InjectMocks
//    private ServiceFacade serviceFacade;
//
//    @Captor
//    private ArgumentCaptor<User> userCaptor;
//
//    @Test
//    @DisplayName("Test create trainer profile by valid data functionality")
//    void givenValidCreateTrainerDto_whenCreateTrainerProfile_thenCreateTrainerProfile() {
//        // given
//        CreateTrainerProfileRequest request = EntityTestData.getValidCreateTrainerProfileRequest();
//        Trainer trainer = EntityTestData.getPersistedTrainerEmilyDavis();
//        User user = EntityTestData.getTransientUserEmilyDavis();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTrainerProfile");
//
//        String errorMessage = "Trainer creation error";
//        String password = "password";
//
//        doNothing().when(bindingResultsService).handle(eq(bindingResult), any(), eq(errorMessage));
//
//        given(createTrainerProfileMapper.map(request))
//                .willReturn(trainer);
//        given(userProfileService.generatePassword())
//                .willReturn(password);
//        given(userService.prepareUserForSave(any(User.class), eq(password)))
//                .willReturn(user);
//
//        // when
//        serviceFacade.createTrainerProfile(request, bindingResult);
//
//        // then
//        verify(trainerService).save(any(Trainer.class));
//        verify(createTrainerProfileMapper).map(any(Trainer.class));
//    }
//
//    @Test
//    @DisplayName("Test create trainer profile by invalid data functionality")
//    void givenInvalidCreateTrainerDto_whenCreateTrainerProfile_thenExceptionIsThrown() {
//        // given
//        CreateTrainerProfileRequest request = EntityTestData.getInvalidCreateTrainerProfileRequest();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTrainerProfile");
//
//        doThrow(EntityPersistException.class).when(bindingResultsService).handle(any(), any(), any());
//
//        // when & then
//        assertThrows(EntityPersistException.class, () -> serviceFacade.createTrainerProfile(request, bindingResult));
//    }
//
//    @Test
//    @DisplayName("Test create trainee profile by valid data functionality")
//    void givenValidCreateTraineeDto_whenCreateTraineeProfile_thenCreateTraineeProfile() {
//        // given
//        CreateTraineeProfileRequest request = EntityTestData.getValidCreateTraineeProfileRequest();
//        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTraineeProfile");
//
//        String errorMessage = "Trainee creation error";
//        String password = "password";
//
//        doNothing().when(bindingResultsService).handle(eq(bindingResult), any(), eq(errorMessage));
//
//        given(createTraineeProfileMapper.map(request))
//                .willReturn(trainee);
//        given(userProfileService.generatePassword())
//                .willReturn(password);
//        given(userService.prepareUserForSave(any(User.class), eq(password)))
//                .willReturn(user);
//
//        // when
//        serviceFacade.createTraineeProfile(request, bindingResult);
//
//        // then
//        verify(traineeService).save(any(Trainee.class));
//        verify(createTraineeProfileMapper).map(any(Trainee.class));
//    }
//
//    @Test
//    @DisplayName("Test create trainee profile by invalid data functionality")
//    void givenInvalidCreateTraineeDto_whenCreateTraineeProfile_thenExceptionIsThrown() {
//        // given
//        CreateTraineeProfileRequest request = EntityTestData.getInvalidCreateTraineeProfileRequest();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTraineeProfile");
//
//        doThrow(EntityPersistException.class).when(bindingResultsService).handle(any(), any(), any());
//
//        // when & then
//        assertThrows(EntityPersistException.class, () -> serviceFacade.createTraineeProfile(request, bindingResult));
//    }
//
//    @Test
//    @DisplayName("Test find trainer profile by valid credentials functionality")
//    void givenValidCredentials_whenFindTrainerProfile_thenFindTrainerProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//        Trainer trainer = EntityTestData.getPersistedTrainerEmilyDavis();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(trainerService.findByUsername(user.getUsername()))
//                .willReturn(trainer);
//
//        // when
//        serviceFacade.findTrainerProfileByUsername(credentials);
//
//        // then
//        verify(trainerService).findByUsername(user.getUsername());
//        verify(trainerProfileMapper).map(trainer);
//    }
//
//    @Test
//    @DisplayName("Test find trainer profile by null credentials functionality")
//    void givenNullCredentials_whenFindTrainerProfile_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getNullAuthCredentials();
//
//        given(authService.authenticate(credentials))
//                .willThrow(AuthenticationException.class);
//
//        // when & then
//        assertThrows(AuthenticationException.class, () -> serviceFacade.findTrainerProfileByUsername(credentials));
//    }
//
//    @Test
//    @DisplayName("Test find trainer profile by invalid credentials functionality")
//    void givenInvalidCredentials_whenFindTrainerProfile_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getInvalidEmilyDavisAuthCredentials();
//
//        given(authService.authenticate(credentials))
//                .willThrow(AuthenticationException.class);
//
//        // when & then
//        assertThrows(AuthenticationException.class, () -> serviceFacade.findTrainerProfileByUsername(credentials));
//    }
//
//    @Test
//    @DisplayName("Test find trainer profile by trainee credentials functionality")
//    void givenTraineeCredentials_whenFindTrainerProfile_thenTrainerProfileNotFound() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(trainerService.findByUsername(user.getUsername()))
//                .willThrow(EntityValidationException.class);
//
//        // when & then
//        assertThrows(EntityValidationException.class, () -> serviceFacade.findTrainerProfileByUsername(credentials));
//    }
//
//    @Test
//    @DisplayName("Test find trainee profile by valid credentials functionality")
//    void givenValidCredentials_whenFindTraineeProfile_thenFindTraineeProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(traineeService.findByUsername(user.getUsername()))
//                .willReturn(trainee);
//
//        // when
//        serviceFacade.findTraineeProfileByUsername(credentials);
//
//        // then
//        verify(traineeService).findByUsername(user.getUsername());
//        verify(traineeProfileMapper).mapToGetTraineeResponse(trainee);
//    }
//
//    @Test
//    @DisplayName("Test find trainee profile by null credentials functionality")
//    void givenNullCredentials_whenFindTraineeProfile_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getNullAuthCredentials();
//
//        given(authService.authenticate(credentials))
//                .willThrow(AuthenticationException.class);
//
//        // when & then
//        assertThrows(AuthenticationException.class, () -> serviceFacade.findTraineeProfileByUsername(credentials));
//    }
//
//    @Test
//    @DisplayName("Test find trainee profile by invalid credentials functionality")
//    void givenInvalidCredentials_whenFindTraineeProfile_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getInvalidJohnDoeAuthCredentials();
//
//        given(authService.authenticate(credentials))
//                .willThrow(AuthenticationException.class);
//
//        // when & then
//        assertThrows(AuthenticationException.class, () -> serviceFacade.findTraineeProfileByUsername(credentials));
//    }
//
//    @Test
//    @DisplayName("Test find trainee profile by trainer credentials functionality")
//    void givenTrainerCredentials_whenFindTraineeProfile_thenTrainerProfileNotFound() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(traineeService.findByUsername(user.getUsername()))
//                .willThrow(EntityValidationException.class);
//
//        // when & then
//        assertThrows(EntityValidationException.class, () -> serviceFacade.findTraineeProfileByUsername(credentials));
//    }
//
//    @Test
//    @DisplayName("Test change password functionality")
//    void givenValidCredentials_whenChangePassword_thenChangePassword() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//        String newPassword = "newPassword";
//        String hashedPassword = "hashedPassword";
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(userProfileService.hashPassword(newPassword))
//                .willReturn(hashedPassword);
//
//        // when
//        serviceFacade.changePassword(newPassword, credentials);
//
//        // then
//        verify(userService).update(userCaptor.capture());
//
//        User capturedUser = userCaptor.getValue();
//        assertThat(capturedUser.getUsername()).isEqualTo(user.getUsername());
//        assertThat(capturedUser.getPassword()).isEqualTo(hashedPassword);
//    }
//
//    @Test
//    @DisplayName("Test update trainer profile by valid data functionality")
//    void givenValidTrainerDto_whenUpdateTrainerProfile_thenUpdateTrainerProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        TrainerProfileRequest request = EntityTestData.getValidTrainerProfileRequest();
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//        Trainer updatedTrainer = EntityTestData.getPersistedTrainerEmilyDavis();
//        Trainer trainer = EntityTestData.getPersistedTrainerEmilyDavis();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTrainerProfile");
//
//        doNothing().when(bindingResultsService).handle(eq(bindingResult), any(), any());
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(trainerProfileMapper.map(request))
//                .willReturn(updatedTrainer);
//        given(trainerService.findByUsername(any()))
//                .willReturn(trainer);
//        given(userProfileService.generateUsername(any(), any()))
//                .willReturn(user.getUsername());
//
//        // when
//        serviceFacade.updateTrainerProfile(request, bindingResult, credentials);
//
//        // then
//        verify(trainerService).update(any(Trainer.class));
//    }
//
//    @Test
//    @DisplayName("Test update trainer profile by invalid data functionality")
//    void givenInvalidTrainerDto_whenUpdateTrainerProfile_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        TrainerProfileRequest request = EntityTestData.getInvalidTrainerProfileRequest();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTrainerProfile");
//
//        doThrow(EntityPersistException.class).when(bindingResultsService).handle(any(), any(), any());
//
//        // when & then
//        assertThrows(EntityPersistException.class, () -> serviceFacade.updateTrainerProfile(request, bindingResult, credentials));
//    }
//
//    @Test
//    @DisplayName("Test update trainee profile by valid data functionality")
//    void givenValidTraineeDto_whenUpdateTraineeProfile_thenUpdateTrainerProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        TraineeProfileRequest request = EntityTestData.getValidTraineeProfileRequest();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//        Trainee updatedTrainee = EntityTestData.getPersistedTraineeJohnDoe();
//        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTraineeProfile");
//
//        doNothing().when(bindingResultsService).handle(eq(bindingResult), any(), any());
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(traineeProfileMapper.map(request))
//                .willReturn(updatedTrainee);
//        given(traineeService.findByUsername(any()))
//                .willReturn(trainee);
//        given(userProfileService.generateUsername(any(), any()))
//                .willReturn(user.getUsername());
//
//        // when
//        serviceFacade.updateTraineeProfile(request, bindingResult, credentials);
//
//        // then
//        verify(traineeService).update(any(Trainee.class));
//    }
//
//    @Test
//    @DisplayName("Test update trainee profile by invalid data functionality")
//    void givenInvalidTraineeDto_whenUpdateTraineeProfile_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        TraineeProfileRequest request = EntityTestData.getInvalidTraineeProfileRequest();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTraineeProfile");
//
//        doThrow(EntityPersistException.class).when(bindingResultsService).handle(any(), any(), any());
//
//        // when & then
//        assertThrows(EntityPersistException.class, () -> serviceFacade.updateTraineeProfile(request, bindingResult, credentials));
//    }
//
//    @Test
//    @DisplayName("Test activate profile when profile is activated functionality")
//    void givenActivatedProfile_whenActivateProfile_thenActivateProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe().toBuilder().isActive(true).build();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.activateProfile(credentials);
//
//        // then
//        verify(userService, never()).update(user);
//    }
//
//    @Test
//    @DisplayName("Test activate profile when profile is deactivated functionality")
//    void givenDeactivatedProfile_whenActivateProfile_thenActivateProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe().toBuilder().isActive(false).build();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.activateProfile(credentials);
//
//        // then
//        verify(userService).update(any(User.class));
//    }
//
//    @Test
//    @DisplayName("Test deactivate profile when profile is deactivated functionality")
//    void givenDeactivatedProfile_whenDeactivateProfile_thenDeactivateProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe().toBuilder().isActive(false).build();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.deactivateProfile(credentials);
//
//        // then
//        verify(userService, never()).update(any(User.class));
//    }
//
//    @Test
//    @DisplayName("Test deactivate profile when profile is activated functionality")
//    void givenActivatedProfile_whenDeactivateProfile_thenDeactivateProfile() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe().toBuilder().isActive(true).build();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.deactivateProfile(credentials);
//
//        // then
//        verify(userService).update(any(User.class));
//    }
//
//    @Test
//    @DisplayName("Test delete trainee by username functionality")
//    void whenDeleteTraineeByUsername_thenDeleteTraineeByUsername() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.deleteTraineeProfileByUsername(credentials);
//
//        // then
//        verify(traineeService).deleteByUsername(user.getUsername());
//    }
//
//    @Test
//    @DisplayName("Test get trainee trainings by criteria functionality")
//    void givenCriteria_whenGetTraineeTrainings_thenTrainingsIsReturned() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//
//        LocalDate from = LocalDate.parse("2020-01-01");
//        LocalDate to = LocalDate.parse("2020-01-02");
//        String trainerName = "Emily";
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.getTraineeTrainingsByCriteria(from, to, trainerName, null, credentials);
//
//        // then
//        verify(traineeService).findTrainingsByCriteria(user.getUsername(), from, to, trainerName, null);
//        verify(trainingMapper).mapList(any());
//    }
//
//    @Test
//    @DisplayName("Test get trainer trainings by criteria functionality")
//    void givenCriteria_whenGetTrainerTrainings_thenTrainingsIsReturned() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//
//        LocalDate from = LocalDate.parse("2020-01-01");
//        LocalDate to = LocalDate.parse("2020-01-02");
//        String traineeName = "John";
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.getTrainerTrainingsByCriteria(from, to, traineeName, null, credentials);
//
//        // then
//        verify(trainerService).findTrainingsByCriteria(user.getUsername(), from, to, traineeName, null);
//        verify(trainingMapper).mapList(any());
//    }
//
//    @Test
//    @DisplayName("Test add training functionality")
//    void givenTrainingRequest_whenAddTraining_thenTrainingIsAdded() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
//        TrainingRequest request = EntityTestData.getValidTrainingRequest();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "addTraining");
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        doNothing().when(bindingResultsService).handle(any(), any(), any());
//
//        // when
//        serviceFacade.addTraining(request, bindingResult, credentials);
//
//        // then
//        verify(trainingMapper).map(request);
//        verify(trainingService).save(any());
//    }
//
//    @Test
//    @DisplayName("Test add training functionality")
//    void givenInvalidTrainingRequest_whenAddTraining_thenExceptionIsThrown() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        TrainingRequest request = EntityTestData.getInvalidTrainingRequest();
//        BindingResult bindingResult = new BeanPropertyBindingResult(request, "addTraining");
//
//        doThrow(EntityPersistException.class).when(bindingResultsService).handle(any(), any(), any());
//
//        // when & then
//        assertThrows(EntityPersistException.class, () -> serviceFacade.addTraining(request, bindingResult, credentials));
//    }
//
//    @Test
//    @DisplayName("Test get trainer not assigned by trainee username functionality")
//    void givenUsername_whenGetTrainerNotAssigned_thenTrainersIsReturned() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserEmilyDavis();
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//
//        // when
//        serviceFacade.getTrainersNotAssignedByTraineeUsername(credentials);
//
//        // then
//        verify(trainerService).getTrainersNotAssignedByTraineeUsername(user.getUsername());
//        verify(trainerProfileMapper).mapList(any());
//    }
//
//    @Test
//    @DisplayName("Test add trainer to trainee functionality")
//    void givenTrainerUsername_whenAddTrainerToTrainee_thenTrainerIsAdded() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();
//        String trainerUsername = "David.Brown";
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(traineeService.findByUsername(user.getUsername()))
//                .willReturn(trainee);
//
//        // when
//        serviceFacade.addTrainerToTrainee(trainerUsername, credentials);
//
//        // then
//        verify(trainerService).findByUsername(trainerUsername);
//        verify(traineeService).update(trainee);
//    }
//
//    @Test
//    @DisplayName("Test remove trainer to trainee functionality")
//    void givenTrainerUsername_whenRemoveTraineesTrainer_thenTrainerIsRemoved() {
//        // given
//        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
//        User user = EntityTestData.getPersistedUserJohnDoe();
//        Trainee trainee = EntityTestData.getPersistedTraineeJohnDoe();
//        String trainerUsername = "Emily.Davis";
//
//        given(authService.authenticate(credentials))
//                .willReturn(user);
//        given(traineeService.findByUsername(user.getUsername()))
//                .willReturn(trainee);
//
//        // when
//        serviceFacade.removeTraineesTrainer(trainerUsername, credentials);
//
//        // then
//        verify(trainerService).findByUsername(trainerUsername);
//        verify(traineeService).update(trainee);
//    }
//}