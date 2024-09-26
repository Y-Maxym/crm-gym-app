package com.gym.crm.app.dto.response;

public record CreateTrainerProfileResponse
        (
                String specialization,
                CreateUserProfileResponse user
        ) {
}
