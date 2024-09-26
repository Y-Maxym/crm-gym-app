package com.gym.crm.app.dto.response;

import java.time.LocalDate;

public record CreateTraineeProfileResponse
        (
                LocalDate dateOfBirth,
                String address,
                CreateUserProfileResponse user
        ) {
}
