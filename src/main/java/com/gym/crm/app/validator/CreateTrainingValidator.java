package com.gym.crm.app.validator;

import com.gym.crm.app.entity.Trainee;
import com.gym.crm.app.entity.Trainer;
import com.gym.crm.app.rest.model.AddTrainingRequest;
import com.gym.crm.app.service.TraineeService;
import com.gym.crm.app.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class CreateTrainingValidator implements Validator {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AddTrainingRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        AddTrainingRequest request = (AddTrainingRequest) target;

        String traineeUsername = request.getTraineeUsername();
        String trainerUsername = request.getTrainerUsername();
        String trainingName = request.getTrainingName();
        LocalDate trainingDate = request.getTrainingDate();
        Integer trainingDuration = request.getTrainingDuration();

        if (isNull(traineeUsername)) {
            errors.rejectValue("traineeUsername", "trainee.username.empty.error", "Trainee username is required");
        }
        if (isNull(trainerUsername)) {
            errors.rejectValue("trainerUsername", "trainer.username.empty.error", "Trainer username is required");
        }
        if (isNull(trainingName)) {
            errors.rejectValue("trainingName", "training.name.empty.error", "Training name is required");
        }
        if (isNull(trainingDate)) {
            errors.rejectValue("trainingDate", "training.date.empty.error", "Training date is required");
        }
        if (isNull(trainingDuration)) {
            errors.rejectValue("trainingDuration", "training.duration.empty.error", "Training duration is required");
        }
    }
}
