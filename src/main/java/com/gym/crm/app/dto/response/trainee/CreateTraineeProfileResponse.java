package com.gym.crm.app.dto.response.trainee;

import com.gym.crm.app.dto.response.user.CreateUserProfileResponse;

import java.time.LocalDate;

public record CreateTraineeProfileResponse
        (
                LocalDate dateOfBirth,
                String address,
                CreateUserProfileResponse user
        ) {
}
