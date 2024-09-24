package com.gym.crm.app.dto.request;

import java.time.LocalDate;

public record TrainingRequest
        (
                TraineeProfileRequest trainee,
                TrainerProfileRequest trainer,
                String trainingName,
                String trainingType,
                LocalDate trainingDate,
                Integer trainingDuration
        ) {
}
