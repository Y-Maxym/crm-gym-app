package com.gym.crm.app.dto.request.trainer;

import com.gym.crm.app.dto.request.user.UserProfileRequest;

public record TrainerProfileRequest
        (
                String specialization,
                UserProfileRequest user
        ) {
}
