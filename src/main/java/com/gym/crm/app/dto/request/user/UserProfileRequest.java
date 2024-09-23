package com.gym.crm.app.dto.request.user;

public record UserProfileRequest
        (
                String firstName,
                String lastName,
                String username
        ) {
}
