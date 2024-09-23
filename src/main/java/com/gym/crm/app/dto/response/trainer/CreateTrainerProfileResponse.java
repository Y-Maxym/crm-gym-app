package com.gym.crm.app.dto.response.trainer;

import com.gym.crm.app.dto.response.user.CreateUserProfileResponse;

public record CreateTrainerProfileResponse
        (
                String specialization,
                CreateUserProfileResponse user
        ) {
}
