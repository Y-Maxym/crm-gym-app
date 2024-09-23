package com.gym.crm.app.dto.request.training;

import com.gym.crm.app.dto.request.trainee.TraineeProfileRequest;
import com.gym.crm.app.dto.request.trainer.TrainerProfileRequest;

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
