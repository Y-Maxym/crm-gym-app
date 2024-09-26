package com.gym.crm.app.dto.response;

import java.time.LocalDate;

public record TrainingResponse
        (
                TraineeProfileResponse traineeUsername,
                TrainerProfileResponse trainerUsername,
                String trainingName,
                String trainingType,
                LocalDate trainingDate,
                Integer trainingDuration
        ) {
}
