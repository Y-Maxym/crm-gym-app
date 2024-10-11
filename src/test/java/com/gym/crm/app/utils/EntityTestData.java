package com.gym.crm.app.utils;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.entity.User;
import com.gym.crm.app.rest.model.ChangePasswordRequest;
import com.gym.crm.app.rest.model.TraineeCreateRequest;
import com.gym.crm.app.rest.model.TrainerCreateRequest;
import com.gym.crm.app.rest.model.UserCredentials;

import java.time.LocalDate;

public class EntityTestData {

    public static User getPersistedUserJohnDoe() {
        return User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getPersistedUserJaneSmith() {
        return User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .username("Jane.Smith")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getPersistedUserMichaelJohnson() {
        return User.builder()
                .id(3L)
                .firstName("Michael")
                .lastName("Johnson")
                .username("Michael.Johnson")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getPersistedUserEmilyDavis() {
        return User.builder()
                .id(4L)
                .firstName("Emily")
                .lastName("Davis")
                .username("Emily.Davis")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getPersistedUserDavidBrown() {
        return User.builder()
                .id(5L)
                .firstName("David")
                .lastName("Brown")
                .username("David.Brown")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static Trainee getPersistedTraineeJohnDoe() {
        return Trainee.builder()
                .user(getPersistedUserJohnDoe())
                .id(1L)
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address1")
                .build();
    }

    public static Trainee getPersistedTraineeJaneSmith() {
        return Trainee.builder()
                .user(getPersistedUserJaneSmith())
                .id(2L)
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address2")
                .build();
    }

    public static Trainee getPersistedTraineeMichaelJohnson() {
        return Trainee.builder()
                .user(getPersistedUserMichaelJohnson())
                .id(3L)
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address3")
                .build();
    }

    public static Trainer getPersistedTrainerEmilyDavis() {
        return Trainer.builder()
                .user(getPersistedUserEmilyDavis())
                .id(1L)
                .specialization(getTrainingTypeYoga())
                .build();
    }

    public static Trainer getPersistedTrainerDavidBrown() {
        return Trainer.builder()
                .user(getPersistedUserDavidBrown())
                .id(2L)
                .specialization(getTrainingTypeFitness())
                .build();
    }

    public static Training getPersistedTrainingEmilyDavis() {
        return Training.builder()
                .id(1L)
                .trainee(getPersistedTraineeJohnDoe())
                .trainer(getPersistedTrainerEmilyDavis())
                .trainingName("Training Emily Davis")
                .trainingType(getTrainingTypeYoga())
                .trainingDate(LocalDate.parse("2020-01-01"))
                .trainingDuration(2)
                .build();
    }

    public static Training getPersistedTrainingDavidBrown() {
        return Training.builder()
                .id(2L)
                .trainee(getPersistedTraineeJaneSmith())
                .trainer(getPersistedTrainerDavidBrown())
                .trainingName("Training David Brown")
                .trainingType(getTrainingTypeYoga())
                .trainingDate(LocalDate.parse("2020-01-02"))
                .trainingDuration(2)
                .build();
    }

    public static User getTransientUserJohnDoe() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getTransientUserJohnDoeWithoutData() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();
    }

    public static User getTransientUserJaneSmith() {
        return User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .username("Jane.Smith")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getTransientUserMichaelJohnson() {
        return User.builder()
                .firstName("Michael")
                .lastName("Johnson")
                .username("Michael.Johnson")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getTransientUserEmilyDavis() {
        return User.builder()
                .firstName("Emily")
                .lastName("Davis")
                .username("Emily.Davis")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static User getTransientUserDavidBrown() {
        return User.builder()
                .firstName("David")
                .lastName("Brown")
                .username("David.Brown")
                .password("hWncieyCBA6S/1dVgKFm+A==:/esUykUhSBey4glFRvVkJHQtGm1KPe67Pxa3ddZi7EI=")
                .isActive(true)
                .build();
    }

    public static Trainee getTransientTraineeJohnDoe() {
        return Trainee.builder()
                .user(getTransientUserJohnDoe())
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address1")
                .build();
    }

    public static Trainee getTransientTraineeJaneSmith() {
        return Trainee.builder()
                .user(getTransientUserJaneSmith())
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address2")
                .build();
    }

    public static Trainee getTransientTraineeMichaelJohnson() {
        return Trainee.builder()
                .user(getTransientUserMichaelJohnson())
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address3")
                .build();
    }

    public static Trainer getTransientTrainerEmilyDavis() {
        return Trainer.builder()
                .user(getTransientUserEmilyDavis())
                .specialization(getTrainingTypeYoga())
                .build();
    }

    public static Trainer getTransientTrainerDavidBrown() {
        return Trainer.builder()
                .user(getTransientUserDavidBrown())
                .specialization(getTrainingTypeFitness())
                .build();
    }

    public static Training getTransientTrainingEmilyDavis() {
        return Training.builder()
                .trainee(getTransientTraineeJaneSmith())
                .trainer(getTransientTrainerEmilyDavis())
                .trainingName("Training Emily Davis")
                .trainingType(getTrainingTypeYoga())
                .trainingDate(LocalDate.parse("2020-01-01"))
                .trainingDuration(2)
                .build();
    }

    public static Training getTransientTrainingDavidBrown() {
        return Training.builder()
                .trainee(getTransientTraineeJohnDoe())
                .trainer(getTransientTrainerDavidBrown())
                .trainingName("Training David Brown")
                .trainingType(getTrainingTypeYoga())
                .trainingDate(LocalDate.parse("2020-01-02"))
                .trainingDuration(2)
                .build();
    }

    public static TrainingType getTrainingTypeYoga() {
        return TrainingType.builder()
                .id(2L)
                .trainingTypeName("Yoga")
                .build();
    }

    public static TrainingType getTrainingTypeFitness() {
        return TrainingType.builder()
                .id(1L)
                .trainingTypeName("Fitness")
                .build();
    }

//    public static CreateUserProfileRequest getValidCreateUserProfileRequest() {
//        return new CreateUserProfileRequest("firstName", "lastName");
//    }
//
//    public static CreateUserProfileRequest getInvalidCreateUserProfileRequest() {
//        return new CreateUserProfileRequest(null, null);
//    }

    public static TrainerCreateRequest getValidCreateTrainerProfileRequest() {
        return new TrainerCreateRequest()
                .firstName("firstName")
                .lastName("lastName")
                .specialization("Yoga");
    }

    public static TrainerCreateRequest getInvalidCreateTrainerProfileRequest() {
        return new TrainerCreateRequest()
                .firstName(null)
                .lastName(null)
                .specialization(null);
    }

    public static TraineeCreateRequest getValidCreateTraineeProfileRequest() {
        return new TraineeCreateRequest()
                .firstName("firstName")
                .lastName("lastName")
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address");
    }

    public static TraineeCreateRequest getInvalidCreateTraineeProfileRequest() {
        return new TraineeCreateRequest()
                .firstName(null)
                .lastName(null)
                .dateOfBirth(null)
                .address(null);
    }

    public static UserCredentials getValidJohnDoeAuthCredentials() {
        return new UserCredentials()
                .username("John.Doe")
                .password("password");
    }

    public static UserCredentials getValidEmilyDavisAuthCredentials() {
        return new UserCredentials()
                .username("Emily.Davis")
                .password("password");
    }

    public static UserCredentials getInvalidJohnDoeAuthCredentials() {
        return new UserCredentials()
                .username("John.Doe")
                .password("incorrect");
    }

    public static UserCredentials getInvalidEmilyDavisAuthCredentials() {
        return new UserCredentials()
                .username("Emily.Davis")
                .password("incorrect");
    }

    public static UserCredentials getNullAuthCredentials() {
        return new UserCredentials()
                .username(null)
                .password(null);
    }

    public static ChangePasswordRequest getValidChangePasswordRequest() {
        return new ChangePasswordRequest()
                .username("Emily.Davis")
                .password("password")
                .newPassword("newPassword");
    }

//    public static UserProfileRequest getValidUserProfileRequest() {
//        return new UserProfileRequest("firstName", "lastName");
//    }
//
//    public static UserProfileRequest getInvalidUserProfileRequest() {
//        return new UserProfileRequest(null, null);
//    }
//
//    public static UpdateTrainerProfileRequest getValidUpdateTrainerProfileRequest() {
//        return new UpdateTrainerProfileRequest("Yoga", getValidUserProfileRequest());
//    }
//
//    public static TrainerProfileRequest getInvalidTrainerProfileRequest() {
//        return new TrainerProfileRequest("Yoga", getInvalidUserProfileRequest());
//    }
//
//    public static TraineeProfileRequest getValidTraineeProfileRequest() {
//        return new TraineeProfileRequest(LocalDate.parse("2000-01-01"), "Address2", getValidUserProfileRequest());
//    }
//
//    public static TraineeProfileRequest getInvalidTraineeProfileRequest() {
//        return new TraineeProfileRequest(LocalDate.parse("2000-01-01"), "Address1", getInvalidUserProfileRequest());
//    }
//
//    public static TrainingRequest getValidTrainingRequest() {
//        return new TrainingRequest(
//                "John.Doe",
//                "David.Brown",
//                "Training name",
//                "Yoga",
//                LocalDate.parse("2021-02-01"),
//                2);
//    }
//
//    public static TrainingRequest getInvalidTrainingRequest() {
//        return new TrainingRequest(
//                null,
//                null,
//                "Training name",
//                "Yoga",
//                LocalDate.parse("2021-02-01"),
//                2);
//    }
}
