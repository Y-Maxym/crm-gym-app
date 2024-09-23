package com.gym.crm.app.dto.request.user;

public record CreateUserProfileRequest
        (
                String firstName,
                String lastName
        ) {
}
