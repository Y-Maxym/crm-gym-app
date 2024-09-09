package com.gym.crm.app.model.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
public final class User {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private boolean isActive;
}
