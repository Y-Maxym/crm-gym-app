package com.gym.crm.app.exception;

import com.gym.crm.app.error.FieldErrorEntity;

import java.util.List;

public class CreateTrainingException extends ApplicationException {

    public CreateTrainingException(List<FieldErrorEntity> errors) {
        super("Training creation error", errors);
    }
}
