package com.gym.crm.app.dto.response.training;

import com.gym.crm.app.dto.response.trainee.TraineeProfileResponse;
import com.gym.crm.app.dto.response.trainer.TrainerProfileResponse;

import java.time.LocalDate;

public record TrainingResponse
        (
                TraineeProfileResponse trainee,
                TrainerProfileResponse trainer,
                String trainingName,
                String trainingType,
                LocalDate trainingDate,
                Integer trainingDuration
        ) {
}
