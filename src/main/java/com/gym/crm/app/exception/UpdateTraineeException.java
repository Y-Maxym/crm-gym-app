package com.gym.crm.app.exception;

import com.gym.crm.app.error.FieldErrorEntity;

import java.util.List;

public class UpdateTraineeException extends ApplicationException {

    public UpdateTraineeException(List<FieldErrorEntity> errors) {
        super("Trainee update error", errors);
    }
}
