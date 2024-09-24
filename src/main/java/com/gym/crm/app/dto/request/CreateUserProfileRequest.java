package com.gym.crm.app.dto.request;

public record CreateUserProfileRequest
        (
                String firstName,
                String lastName
        ) {
}
