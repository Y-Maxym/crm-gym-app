package com.gym.crm.app.rest.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.gym.crm.app.exception.AuthenticationException;
import com.gym.crm.app.exception.EntityPersistException;
import com.gym.crm.app.exception.EntityValidationException;
import com.gym.crm.app.exception.PasswordOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<ErrorMessage> handleException(EntityValidationException e) {
        ErrorMessage error = new ErrorMessage(400, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleException(AuthenticationException e) {
        ErrorMessage error = new ErrorMessage(401, e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(EntityPersistException.class)
    public ResponseEntity<ValidationError> handleException(EntityPersistException e) {
        ValidationError error = new ValidationError(400, e.getMessage(), e.getErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PasswordOperationException.class)
    public ResponseEntity<ErrorMessage> handleException(PasswordOperationException e) {
        ErrorMessage error = new ErrorMessage(400, e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleException(HttpMessageNotReadableException e) {
        return handleHttpMessageNotReadableException(e);
    }

    private ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        int code = 400;
        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException) {
            message = "Invalid field format";
            code = 1012;
        } else if (cause instanceof ValueInstantiationException && cause.getCause() != null) {
            message = cause.getCause().getMessage();
            code = 1033;
        } else if (cause instanceof UnrecognizedPropertyException ex) {
            message = "Unrecognized field %s".formatted(ex.getPropertyName());
            code = 1034;
        }

        ErrorMessage error = new ErrorMessage(code, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
