package com.gym.crm.app.rest.exception;

public record ErrorMessage
        (
                int code,
                String message
        ) {
}
