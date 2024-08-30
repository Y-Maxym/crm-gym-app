package com.crm.gym.app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
}
