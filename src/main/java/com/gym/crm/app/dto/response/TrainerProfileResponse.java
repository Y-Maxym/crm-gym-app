package com.gym.crm.app.dto.response;

public record TrainerProfileResponse
        (
                String specialization,
                UserProfileResponse user
        ) {
}
