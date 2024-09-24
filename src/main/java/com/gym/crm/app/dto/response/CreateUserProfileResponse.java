package com.gym.crm.app.dto.response;

public record CreateUserProfileResponse
        (
                String firstName,
                String lastName,
                String username,
                String password
        ) {
}
