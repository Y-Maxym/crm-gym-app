package com.gym.crm.app.dto.request.trainer;

import com.gym.crm.app.dto.request.user.CreateUserProfileRequest;

public record CreateTrainerProfileRequest
        (
                String specialization,
                CreateUserProfileRequest user
        ) {
}
