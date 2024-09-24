package com.gym.crm.app.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TrainingRequest
        (
                TraineeProfileRequest trainee,
                TrainerProfileRequest trainer,

                @NotEmpty(message = "Training name should not be empty")
                String trainingName,

                String trainingType,

                @NotNull(message = "Training date should not be null")
                LocalDate trainingDate,

                @NotNull(message = "Training duration should not be null")
                @Min(value = 1, message = "Value should be greater of equals 1")
                Integer trainingDuration
        ) {
}
