package com.gym.crm.app.dto.request;

public record CreateTrainerProfileRequest
        (
                String specialization,
                CreateUserProfileRequest user
        ) {
}
