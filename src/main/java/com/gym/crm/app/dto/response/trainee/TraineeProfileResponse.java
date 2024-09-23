package com.gym.crm.app.dto.response.trainee;

import com.gym.crm.app.dto.response.user.UserProfileResponse;

import java.time.LocalDate;

public record TraineeProfileResponse
        (
                LocalDate dateOfBirth,
                String address,
                UserProfileResponse user
        ) {
}

