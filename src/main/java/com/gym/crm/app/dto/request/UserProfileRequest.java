package com.gym.crm.app.dto.request;

public record UserProfileRequest
        (
                String firstName,
                String lastName,
                String username
        ) {
}
