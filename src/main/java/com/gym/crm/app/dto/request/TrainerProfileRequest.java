package com.gym.crm.app.dto.request;

public record TrainerProfileRequest
        (
                String specialization,
                UserProfileRequest user
        ) {
}
