package com.gym.crm.app.dto.response;

import java.time.LocalDate;

public record TraineeProfileResponse
        (
                LocalDate dateOfBirth,
                String address,
                UserProfileResponse user
        ) {
}
