package com.gym.crm.app.it;

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
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.exception.CreateTraineeException;
import com.gym.crm.app.exception.CreateTrainerException;
import com.gym.crm.app.exception.CreateTrainingException;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.exception.UpdateTraineeException;
import com.gym.crm.app.exception.UpdateTrainerException;
import com.gym.crm.app.facade.ServiceFacade;
import com.gym.crm.app.service.common.UserProfileService;
import com.gym.crm.app.utils.EntityTestData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@Transactional
class ItServiceFacadeTest extends AbstractTestFacade {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ServiceFacade serviceFacade;

    @Test
    @DisplayName("Test create trainer profile by valid data functionality")
    public void givenValidCreateTrainerDto_whenCreateTrainerProfile_thenCreateTrainerProfile() {
        // given
        CreateTrainerProfileRequest request = EntityTestData.getValidCreateTrainerProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTrainerProfile");

        // when
        CreateTrainerProfileResponse actual = serviceFacade.createTrainerProfile(request, bindingResult);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.user().firstName()).isEqualTo(request.user().firstName());
        assertThat(actual.user().lastName()).isEqualTo(request.user().lastName());
        assertThat(actual.specialization()).isEqualTo(request.specialization());
        assertThat(actual.user().username()).isNotNull();
        assertThat(actual.user().password()).isNotNull();
    }

    @Test
    @DisplayName("Test create trainer profile by invalid data functionality")
    public void givenInvalidCreateTrainerDto_whenCreateTrainerProfile_thenExceptionIsThrown() {
        // given
        CreateTrainerProfileRequest request = EntityTestData.getInvalidCreateTrainerProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTrainerProfile");

        bindingResult.rejectValue("user.firstName", "invalid.firstName");
        bindingResult.rejectValue("user.lastName", "invalid.lastName");

        // when
        CreateTrainerException ex = assertThrows(CreateTrainerException.class, () -> serviceFacade.createTrainerProfile(request, bindingResult));

        // then
        assertThat(ex.getMessage()).isEqualTo("Trainer creation error");
    }

    @Test
    @DisplayName("Test create trainee profile by valid data functionality")
    public void givenValidCreateTraineeDto_whenCreateTraineeProfile_thenCreateTraineeProfile() {
        // given
        CreateTraineeProfileRequest request = EntityTestData.getValidCreateTraineeProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTrainerProfile");

        // when
        CreateTraineeProfileResponse actual = serviceFacade.createTraineeProfile(request, bindingResult);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.user().firstName()).isEqualTo(request.user().firstName());
        assertThat(actual.user().lastName()).isEqualTo(request.user().lastName());
        assertThat(actual.dateOfBirth()).isEqualTo(request.dateOfBirth());
        assertThat(actual.address()).isEqualTo(request.address());
        assertThat(actual.user().username()).isNotNull();
        assertThat(actual.user().password()).isNotNull();
    }

    @Test
    @DisplayName("Test create trainee profile by invalid data functionality")
    public void givenInvalidCreateTraineeDto_whenCreateTraineeProfile_thenExceptionIsThrown() {
        // given
        CreateTraineeProfileRequest request = EntityTestData.getInvalidCreateTraineeProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "createTrainerProfile");

        bindingResult.rejectValue("user.firstName", "invalid.firstName");
        bindingResult.rejectValue("user.lastName", "invalid.lastName");

        // when
        CreateTraineeException ex = assertThrows(CreateTraineeException.class, () -> serviceFacade.createTraineeProfile(request, bindingResult));

        // then
        assertThat(ex.getMessage()).isEqualTo("Trainee creation error");
    }

    @Test
    @DisplayName("Test find trainer profile by valid credentials functionality")
    public void givenValidCredentials_whenFindTrainerProfile_thenFindTrainerProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();

        // when
        TrainerProfileResponse actual = serviceFacade.findTrainerProfileByUsername(credentials);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.user().firstName()).isEqualTo("Emily");
        assertThat(actual.user().lastName()).isEqualTo("Davis");
    }

    @Test
    @DisplayName("Test find trainer profile by null credentials functionality")
    public void givenNullCredentials_whenFindTrainerProfile_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getNullAuthCredentials();

        // when
        AuthenticationException ex = assertThrows(AuthenticationException.class, () -> serviceFacade.findTrainerProfileByUsername(credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Invalid username or password");
        assertThat(ex.getCause()).isInstanceOf(EntityValidationException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Entity cannot be null");
    }

    @Test
    @DisplayName("Test find trainer profile by invalid credentials functionality")
    public void givenInvalidCredentials_whenFindTrainerProfile_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getInvalidEmilyDavisAuthCredentials();

        // when
        AuthenticationException ex = assertThrows(AuthenticationException.class, () -> serviceFacade.findTrainerProfileByUsername(credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Invalid username or password");
        assertThat(ex.getCause()).isNull();
    }

    @Test
    @DisplayName("Test find trainer profile by trainee credentials functionality")
    public void givenTraineeCredentials_whenFindTrainerProfile_thenTrainerProfileNotFound() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> serviceFacade.findTrainerProfileByUsername(credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Trainer with username %s not found".formatted(credentials.username()));
    }

    @Test
    @DisplayName("Test find trainee profile by valid credentials functionality")
    public void givenValidCredentials_whenFindTraineeProfile_thenFindTraineeProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        TraineeProfileResponse actual = serviceFacade.findTraineeProfileByUsername(credentials);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.user().firstName()).isEqualTo("John");
        assertThat(actual.user().lastName()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("Test find trainee profile by null credentials functionality")
    public void givenNullCredentials_whenFindTraineeProfile_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getNullAuthCredentials();

        // when
        AuthenticationException ex = assertThrows(AuthenticationException.class, () -> serviceFacade.findTraineeProfileByUsername(credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Invalid username or password");
        assertThat(ex.getCause()).isInstanceOf(EntityValidationException.class);
        assertThat(ex.getCause().getMessage()).isEqualTo("Entity cannot be null");
    }

    @Test
    @DisplayName("Test find trainee profile by invalid credentials functionality")
    public void givenInvalidCredentials_whenFindTraineeProfile_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getInvalidJohnDoeAuthCredentials();

        // when
        AuthenticationException ex = assertThrows(AuthenticationException.class, () -> serviceFacade.findTraineeProfileByUsername(credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Invalid username or password");
        assertThat(ex.getCause()).isNull();
    }

    @Test
    @DisplayName("Test find trainee profile by trainer credentials functionality")
    public void givenTrainerCredentials_whenFindTraineeProfile_thenTrainerProfileNotFound() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();

        // when
        EntityValidationException ex = assertThrows(EntityValidationException.class, () -> serviceFacade.findTraineeProfileByUsername(credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Trainee with username %s not found".formatted(credentials.username()));
    }

    @Test
    @DisplayName("Test change password functionality")
    public void givenValidCredentials_whenChangePassword_thenChangePassword() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
        String newPassword = "newPassword";

        // when
        serviceFacade.changePassword(newPassword, credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult();

        assertTrue(userProfileService.isPasswordCorrect(newPassword, actual.getPassword()));
    }

    @Test
    @DisplayName("Test update trainer profile by valid data functionality")
    public void givenValidTrainerDto_whenUpdateTrainerProfile_thenUpdateTrainerProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
        TrainerProfileRequest request = EntityTestData.getValidTrainerProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTrainerProfile");

        // when
        serviceFacade.updateTrainerProfile(request, bindingResult, credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.firstName = :firstName", User.class)
                .setParameter("firstName", request.user().firstName())
                .getSingleResult();

        assertThat(actual).isNotNull();
        assertTrue(userProfileService.isPasswordCorrect(credentials.password(), actual.getPassword()));
    }

    @Test
    @DisplayName("Test update trainer profile by invalid data functionality")
    public void givenInvalidTrainerDto_whenUpdateTrainerProfile_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();
        TrainerProfileRequest request = EntityTestData.getInvalidTrainerProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTrainerProfile");

        bindingResult.rejectValue("user.firstName", "invalid.firstName");
        bindingResult.rejectValue("user.lastName", "invalid.lastName");

        // when
        UpdateTrainerException ex = assertThrows(UpdateTrainerException.class, () -> serviceFacade.updateTrainerProfile(request, bindingResult, credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Trainer update error");
    }

    @Test
    @DisplayName("Test update trainee profile by valid data functionality")
    public void givenValidTraineeDto_whenUpdateTraineeProfile_thenUpdateTrainerProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        TraineeProfileRequest request = EntityTestData.getValidTraineeProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTraineeProfile");

        // when
        serviceFacade.updateTraineeProfile(request, bindingResult, credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.firstName = :firstName", User.class)
                .setParameter("firstName", request.user().firstName())
                .getSingleResult();

        assertThat(actual).isNotNull();
        assertTrue(userProfileService.isPasswordCorrect(credentials.password(), actual.getPassword()));
    }

    @Test
    @DisplayName("Test update trainee profile by invalid data functionality")
    public void givenInvalidTraineeDto_whenUpdateTraineeProfile_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        TraineeProfileRequest request = EntityTestData.getInvalidTraineeProfileRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "updateTrainerProfile");

        bindingResult.rejectValue("user.firstName", "invalid.firstName");
        bindingResult.rejectValue("user.lastName", "invalid.lastName");

        // when
        UpdateTraineeException ex = assertThrows(UpdateTraineeException.class, () -> serviceFacade.updateTraineeProfile(request, bindingResult, credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Trainee update error");
    }

    @Test
    @DisplayName("Test activate profile when profile is activated functionality")
    public void givenActivatedProfile_whenActivateProfile_thenActivateProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        serviceFacade.activateProfile(credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult();

        assertTrue(actual.isActive());
    }

    @Test
    @DisplayName("Test activate profile when profile is deactivated functionality")
    public void givenDeactivatedProfile_whenActivateProfile_thenActivateProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        entityManager.createQuery("UPDATE User u SET u.isActive = false WHERE u.username = :username")
                .setParameter("username", credentials.username())
                .executeUpdate();

        boolean beforeActivate = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult()
                .isActive();

        // when
        serviceFacade.activateProfile(credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult();

        assertFalse(beforeActivate);
        assertTrue(actual.isActive());
    }

    @Test
    @DisplayName("Test deactivate profile when profile is deactivated functionality")
    public void givenDeactivatedProfile_whenDeactivateProfile_thenDeactivateProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        entityManager.createQuery("UPDATE User u SET u.isActive = false WHERE u.username = :username")
                .setParameter("username", credentials.username())
                .executeUpdate();

        boolean beforeActivate = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult()
                .isActive();

        // when
        serviceFacade.deactivateProfile(credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult();

        assertFalse(beforeActivate);
        assertFalse(actual.isActive());
    }

    @Test
    @DisplayName("Test deactivate profile when profile is activated functionality")
    public void givenActivatedProfile_whenDeactivateProfile_thenDeactivateProfile() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        serviceFacade.deactivateProfile(credentials);

        // then
        User actual = entityManager.createQuery("FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", credentials.username())
                .getSingleResult();

        assertFalse(actual.isActive());
    }

    @Test
    @DisplayName("Test delete trainee by username functionality")
    public void whenDeleteTraineeByUsername_thenDeleteTraineeByUsername() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        List<Trainee> beforeDelete = entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username", Trainee.class)
                .setParameter("username", credentials.username())
                .getResultList();

        serviceFacade.deleteTraineeProfileByUsername(credentials);

        // then
        List<Trainee> actual = entityManager.createQuery("FROM Trainee t WHERE t.user.username = :username", Trainee.class)
                .setParameter("username", credentials.username())
                .getResultList();

        assertThat(beforeDelete).isNotEmpty();
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test get trainee trainings by criteria functionality")
    public void givenCriteria_whenGetTraineeTrainings_thenTrainingsIsReturned() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2020-01-02");
        String trainerName = "Emily";

        // when
        Set<TrainingResponse> actual = serviceFacade.getTraineeTrainingsByCriteria(from, to, trainerName, null, credentials);

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("Test get trainer trainings by criteria functionality")
    public void givenCriteria_whenGetTrainerTrainings_thenTrainingsIsReturned() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();

        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2020-01-02");
        String traineeName = "John";

        // when
        Set<TrainingResponse> actual = serviceFacade.getTrainerTrainingsByCriteria(from, to, traineeName, null, credentials);

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("Test add training functionality")
    public void givenTrainingRequest_whenAddTraining_thenTrainingIsAdded() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();

        TrainingRequest request = EntityTestData.getValidTrainingRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "addTraining");

        // when
        serviceFacade.addTraining(request, bindingResult, credentials);

        // then
        Training actual = entityManager.createQuery("FROM Training t WHERE t.trainingName = :trainingName", Training.class)
                .setParameter("trainingName", request.trainingName())
                .getSingleResult();

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("Test add training functionality")
    public void givenInvalidTrainingRequest_whenAddTraining_thenExceptionIsThrown() {
        // given
        AuthCredentials credentials = EntityTestData.getValidEmilyDavisAuthCredentials();

        TrainingRequest request = EntityTestData.getInvalidTrainingRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "addTraining");

        bindingResult.rejectValue("traineeUsername", "trainee.username");
        bindingResult.rejectValue("trainerUsername", "trainer.username");

        // when
        CreateTrainingException ex = assertThrows(CreateTrainingException.class, () -> serviceFacade.addTraining(request, bindingResult, credentials));

        // then
        assertThat(ex.getMessage()).isEqualTo("Training creation error");
    }

    @Test
    @DisplayName("Test get trainer not assigned by trainee username functionality")
    public void givenUsername_whenGetTrainerNotAssigned_thenTrainersIsReturned() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();

        // when
        List<TrainerProfileResponse> actual = serviceFacade.getTrainersNotAssignedByTraineeUsername(credentials);

        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("Test add trainer to trainee functionality")
    public void givenTrainerUsername_whenAddTrainerToTrainee_thenTrainerIsAdded() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String trainerUsername = "David.Brown";

        List<Trainer> beforeAdd = entityManager.createQuery("SELECT t.trainers FROM Trainee t WHERE t.user.username = :username", Trainer.class)
                .setParameter("username", credentials.username())
                .getResultList();

        // when
        serviceFacade.addTrainerToTrainee(trainerUsername, credentials);

        // then
        List<Trainer> actual = entityManager.createQuery("SELECT t.trainers FROM Trainee t WHERE t.user.username = :username", Trainer.class)
                .setParameter("username", credentials.username())
                .getResultList();

        assertThat(beforeAdd).hasSize(1);
        assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Test remove trainer to trainee functionality")
    public void givenTrainerUsername_whenRemoveTraineesTrainer_thenTrainerIsRemoved() {
        // given
        AuthCredentials credentials = EntityTestData.getValidJohnDoeAuthCredentials();
        String trainerUsername = "Emily.Davis";

        List<Trainer> beforeAdd = entityManager.createQuery("SELECT t.trainers FROM Trainee t WHERE t.user.username = :username", Trainer.class)
                .setParameter("username", credentials.username())
                .getResultList();

        // when
        serviceFacade.removeTraineesTrainer(trainerUsername, credentials);

        // then
        List<Trainer> actual = entityManager.createQuery("SELECT t.trainers FROM Trainee t WHERE t.user.username = :username", Trainer.class)
                .setParameter("username", credentials.username())
                .getResultList();

        assertThat(beforeAdd).hasSize(1);
        assertThat(actual).isEmpty();
    }
}