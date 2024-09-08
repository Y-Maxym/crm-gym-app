package com.gym.crm.app.utils;

import com.gym.crm.app.model.entity.Trainee;
import com.gym.crm.app.model.entity.Trainer;
import com.gym.crm.app.model.entity.Training;
import com.gym.crm.app.model.entity.User;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataUtils {

    public static User getUserJohnDoe() {
        return User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .build();
    }

    public static User getUserJaneSmith() {
        return User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .isActive(true)
                .build();
    }

    public static User getUserMichaelJohnson() {
        return User.builder()
                .id(3L)
                .firstName("Michael")
                .lastName("Johnson")
                .isActive(true)
                .build();
    }

    public static User getUserEmilyDavis() {
        return User.builder()
                .id(4L)
                .firstName("Emily")
                .lastName("Davis")
                .isActive(true)
                .build();
    }

    public static User getUserDavidBrown() {
        return User.builder()
                .id(5L)
                .firstName("David")
                .lastName("Brown")
                .isActive(true)
                .build();
    }

    public static Trainee getTraineeJohnDoe() {
        return Trainee.builder()
                .userId(1L)
                .id(1L)
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address1")
                .build();
    }

    public static Trainee getTraineeJaneSmith() {
        return Trainee.builder()
                .userId(2L)
                .id(2L)
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address2")
                .build();
    }

    public static Trainee getTraineeMichaelJohnson() {
        return Trainee.builder()
                .userId(3L)
                .id(3L)
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .address("Address3")
                .build();
    }

    public static Trainer getTrainerEmilyDavis() {
        return Trainer.builder()
                .userId(4L)
                .id(1L)
                .specializationId(1L)
                .build();
    }

    public static Trainer getTrainerDavidBrown() {
        return Trainer.builder()
                .userId(5L)
                .id(2L)
                .specializationId(2L)
                .build();
    }

    public static Training getTrainingEmilyDavis() {
        return Training.builder()
                .id(1L)
                .traineeId(1L)
                .trainerId(1L)
                .trainingName("Training Emily Davis")
                .trainingTypeId(1L)
                .trainingDate(LocalDateTime.parse("2020-01-01T10:00:00"))
                .trainingDuration(Duration.ofHours(2))
                .build();
    }

    public static Training getTrainingDavidBrown() {
        return Training.builder()
                .id(2L)
                .traineeId(2L)
                .trainerId(2L)
                .trainingName("Training David Brown")
                .trainingTypeId(1L)
                .trainingDate(LocalDateTime.parse("2020-01-02T12:00:00"))
                .trainingDuration(Duration.ofHours(2))
                .build();
    }
}
