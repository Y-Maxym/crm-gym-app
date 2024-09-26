package com.gym.crm.app.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record TrainerProfileRequest
        (
                @NotEmpty(message = "Specialization should not be empty")
                String specialization,
                UserProfileRequest user
        ) {
}
