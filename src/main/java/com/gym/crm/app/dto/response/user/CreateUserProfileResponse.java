package com.gym.crm.app.dto.response.user;

public record CreateUserProfileResponse
        (
                String firstName,
                String lastName,
                String username,
                String password
        ) {
}
