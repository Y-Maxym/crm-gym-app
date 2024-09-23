package com.gym.crm.app.dto.response.trainer;

import com.gym.crm.app.dto.response.user.UserProfileResponse;

public record TrainerProfileResponse
        (
                String specialization,
                UserProfileResponse user
        ) {
}

