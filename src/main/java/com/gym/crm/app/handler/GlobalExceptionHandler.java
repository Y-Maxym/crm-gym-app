package com.gym.crm.app.handler;

import com.gym.crm.app.error.ErrorMessage;
import com.gym.crm.app.error.ValidationError;
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.exception.EntityPersistException;
import com.gym.crm.app.exception.EntityValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<ErrorMessage> handleException(EntityValidationException e) {
        ErrorMessage error = new ErrorMessage(400, e.getMessage());

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleException(AuthenticationException e) {
        ErrorMessage error = new ErrorMessage(400, e.getMessage());

        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(EntityPersistException.class)
    public ResponseEntity<ValidationError> handleException(EntityPersistException e) {
        ValidationError error = new ValidationError(400, e.getMessage(), e.getErrors());

        return ResponseEntity.status(400).body(error);
    }
}
