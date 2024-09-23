package com.gym.crm.app.dto.request.trainee;

import com.gym.crm.app.dto.request.user.UserProfileRequest;

import java.time.LocalDate;

public record TraineeProfileRequest
        (
                LocalDate dateOfBirth,
                String address,
                UserProfileRequest user
        ) {
}
