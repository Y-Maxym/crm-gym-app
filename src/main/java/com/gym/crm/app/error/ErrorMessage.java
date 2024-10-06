package com.gym.crm.app.error;

public record ErrorMessage
        (
                int code,
                String message
        ) {
}
