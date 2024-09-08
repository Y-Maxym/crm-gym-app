package com.gym.crm.app.exception;

public class StorageNotFoundException extends RuntimeException {

    public StorageNotFoundException(String message) {
        super(message);
    }
}
