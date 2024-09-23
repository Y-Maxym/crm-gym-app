package com.gym.crm.app.utils;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.entity.Training;
import com.gym.crm.app.entity.TrainingType;
import com.gym.crm.app.entity.User;

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
                .id(3L)
                .trainingTypeName("Yoga")
                .build();
    }

    public static TrainingType getTrainingTypeFitness() {
        return TrainingType.builder()
                .id(2L)
                .trainingTypeName("Fitness")
                .build();
    }
}
