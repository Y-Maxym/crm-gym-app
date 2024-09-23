package com.gym.crm.app.dto.request.trainee;

import com.gym.crm.app.dto.request.user.CreateUserProfileRequest;

import java.time.LocalDate;

public record CreateTraineeProfileRequest
        (
                LocalDate dateOfBirth,
                String address,
                CreateUserProfileRequest user
        ) {
}
