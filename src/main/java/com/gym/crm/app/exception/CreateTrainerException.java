package com.gym.crm.app.exception;

import com.gym.crm.app.error.FieldErrorEntity;

import java.util.List;

public class CreateTrainerException extends ApplicationException {

    public CreateTrainerException(List<FieldErrorEntity> errors) {
        super("Trainer creation error", errors);
    }
}
