package com.gym.crm.app.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateTrainerProfileRequest
        (
                @NotEmpty(message = "Specialization should not be empty")
                String specialization,

                CreateUserProfileRequest user
        ) {
}
