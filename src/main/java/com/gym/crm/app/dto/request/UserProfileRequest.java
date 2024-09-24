package com.gym.crm.app.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UserProfileRequest
        (
                @NotEmpty(message = "First name should not be empty")
                String firstName,

                @NotEmpty(message = "Last name should not be empty")
                String lastName,

                @NotEmpty(message = "Username should not be empty")
                String username
        ) {
}
