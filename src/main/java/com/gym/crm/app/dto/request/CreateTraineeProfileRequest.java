package com.gym.crm.app.dto.request;

import java.time.LocalDate;

public record CreateTraineeProfileRequest
        (
                LocalDate dateOfBirth,
                String address,
                CreateUserProfileRequest user
        ) {
}
