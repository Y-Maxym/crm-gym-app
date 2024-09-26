package com.gym.crm.app.dto.request;

import java.time.LocalDate;

public record TraineeProfileRequest
        (
                LocalDate dateOfBirth,
                String address,
                UserProfileRequest user
        ) {
}
