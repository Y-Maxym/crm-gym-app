package com.gym.crm.app.exception;

import com.gym.crm.app.error.FieldErrorEntity;

import java.util.List;

public class CreateTraineeException extends ApplicationException {

    public CreateTraineeException(List<FieldErrorEntity> errors) {
        super("Trainee creation error", errors);
    }
}
