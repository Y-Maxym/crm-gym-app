package com.gym.crm.app.exception;

import com.gym.crm.app.error.FieldErrorEntity;

import java.util.List;

public class UpdateTrainerException extends ApplicationException {

    public UpdateTrainerException(List<FieldErrorEntity> errors) {
        super("Trainer update error", errors);
    }
}
