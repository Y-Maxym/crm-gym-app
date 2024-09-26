package com.gym.crm.app.exception;

import com.gym.crm.app.error.FieldErrorEntity;

import java.util.List;

public class EntityPersistException extends ApplicationException {

    public EntityPersistException(String message, List<FieldErrorEntity> errors) {
        super(message, errors);
    }
}
